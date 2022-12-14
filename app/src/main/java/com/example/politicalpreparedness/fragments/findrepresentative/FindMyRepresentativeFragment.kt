package com.example.politicalpreparedness.fragments.findrepresentative

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.app.ActivityCompat
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.adapters.CurrentElectionAdapter
import com.example.politicalpreparedness.databinding.FragmentFindMyRepresentativeMotionLayoutBinding
import com.example.politicalpreparedness.network.database.CurrentElectionDao
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.network.retrofit.ElectionsAPI
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import com.example.politicalpreparedness.util.Constants
import com.example.politicalpreparedness.util.Constants.ADDRESS1
import com.example.politicalpreparedness.util.Constants.ADDRESS2
import com.example.politicalpreparedness.util.Constants.CITY
import com.example.politicalpreparedness.util.Constants.ZIPCODE
import com.example.politicalpreparedness.viewmodels.findrepresentative.FindRepresentativeViewModel
import com.example.politicalpreparedness.viewmodels.findrepresentative.FindRepresentativeViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

val MY_PERMISSIONS_REQUEST_LOCATION = 0

@AndroidEntryPoint
class FindMyRepresentativeFragment : Fragment(), CurrentElectionAdapter.OnItemClickListener {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var otherList: ArrayList<String>

    @Inject
    lateinit var retro: ElectionsAPI

    @Inject
    lateinit var db: ElectionDatabase

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var dao: CurrentElectionDao

    @Inject
    lateinit var repo: CurrentElectionRepository


    lateinit var binding: FragmentFindMyRepresentativeMotionLayoutBinding

    private lateinit var viewModel: FindRepresentativeViewModel

    var stateChosen: String? = null
    lateinit var adap:ArrayAdapter<CharSequence>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindMyRepresentativeMotionLayoutBinding.inflate(inflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())


        val viewModelFactory =FindRepresentativeViewModelFactory(db, application, repo,null,this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FindRepresentativeViewModel::class.java)


        otherList = ArrayList<String>(Arrays.asList(*resources.getStringArray(R.array.states)))
        val spinner = binding.spinnerState
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.states,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adap= adapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
                AdapterView.OnItemSelectedListener {
                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val state = binding.spinnerState.selectedItem.toString()
                    Log.i("TAG", "onCreateView: $state")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val state = binding.spinnerState.selectedItem.toString()
                    Log.i("TAG", "onCreateView1: $state")
                    stateChosen=state
                    setState(state)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    val state = binding.spinnerState.selectedItem.toString()
                    Log.i("TAG", "onCreateView2: $state")
                }
            }
        }




//        viewModel.addressOne.observe(viewLifecycleOwner){it->
//            binding.etAddressLine1.setText(it.toString())
//
//        }

        binding.apply {
            etAddressLine1.setOnClickListener {
                viewModel.address1 =it.toString()
                viewModel.state.set(ADDRESS1,it.toString())
            }
            etAddressLine2.setOnClickListener {
                viewModel.address2 =it.toString()
                viewModel.state.set(ADDRESS2,it.toString())
            }

            etCity.setOnClickListener {
                viewModel.city =it.toString()
                viewModel.state.set(CITY,it.toString())
            }

            etZipcode.setOnClickListener {
                viewModel.zipCode =it.toString()
                viewModel.state.set(ZIPCODE,it.toString())
            }


            etAddressLine1.setText(viewModel.address1)
            etAddressLine2.setText(viewModel.address1)
            etCity.setText(viewModel.address1)
            etZipcode.setText(viewModel.address1)

        }





        //FIND MY REPRESENTATIVE BUTTON via address typed
        binding.buttonFindMyRepresentative.setOnClickListener {


            val allFilled: Boolean =
                (binding.etAddressLine1.text.isEmpty() || binding.etAddressLine2.text.isEmpty() || binding.etCity.text.isEmpty() || binding.etZipcode.text.isEmpty())
            if (allFilled) {
                Snackbar.make(
                    requireActivity(), requireView(),
                    "You need to fill out all fileds.",
                    Snackbar.LENGTH_LONG
                ).show()
                val state = binding.spinnerState.selectedItem.toString()
                Log.i("TAG", "onCreateView1: $state")
            } else {
                val address1 = binding.etAddressLine1.text.toString()
                val address2 = binding.etAddressLine2.text.toString()
                val city = binding.etCity.text.toString()
                val zipCode = binding.etZipcode.text.toString()
                val state = binding.spinnerState.selectedItem.toString()
                Log.i("TAG", "onCreateView: $state")
                val addressToBeSent = address1.plus(" ").plus(address2).plus(" ").plus(city).plus(" ").plus(state).plus(" ").plus(zipCode)

                viewModel.getRepresentativesViaAddress(addressToBeSent)
//                viewModel.getRepresentativeProfiles(address1.plus(" ").plus(address2), city, zipCode, state)


            }

        }

        //USE MY LOCATION BUTTON - finds rep via location
        binding.buttonUseMyLocation.setOnClickListener {
            checkLocationPermission()
            val permissionForLocationGiven = checkLocationPermission()

            if (permissionForLocationGiven) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        // use your location object
                        // get latitude , longitude and other info from this
                        val geocoder: Geocoder
                        val addresses: List<Address>
                        geocoder = Geocoder(requireContext(), Locale.getDefault())
                        addresses =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)

                        val address0 = addresses.get(0).subThoroughfare
                        val address1 = addresses.get(0).thoroughfare
                        val city = addresses.get(0).locality
                        val state = addresses.get(0).adminArea
                        val zipcode = addresses.get(0).postalCode

                        val c = otherList.indexOf(state)
                        binding.spinnerState.setSelection(c)
                        binding.etAddressLine1.setText(addresses.get(0).subThoroughfare)
                        binding.etAddressLine2.setText(addresses.get(0).thoroughfare)
                        binding.etCity.setText(addresses.get(0).locality)
                        binding.etZipcode.setText(addresses.get(0).postalCode)


                        val addressToBeSent = address0.plus(" ").plus(address1).plus(" ").plus(city).plus(" ").plus(state).plus(" ").plus(zipcode)

                        viewModel.getRepresentativesViaAddress(addressToBeSent)


                    }
                }
            }

        }
        viewModel.representativesProfiles.observe(viewLifecycleOwner) { it ->
            viewModel.getRepAdapter()
        }
        viewModel.representativesAdapt.observe(viewLifecycleOwner) { response ->
            binding.recyclerviewRepresentativesTwo.adapter = response
            binding.recyclerviewRepresentativesTwo.layoutManager =
                LinearLayoutManager(requireContext())

        }



        return binding.root



    }

    private fun setState(state: String) {
        viewModel.stateAddress= state.toString()
    }


    override fun onResume() {
        super.onResume()
        binding.etAddressLine1.setText(viewModel.address1)
        binding.etAddressLine2.setText(viewModel.address2)
        binding.etCity.setText(viewModel.city)
        binding.etZipcode.setText(viewModel.zipCode)
        if(stateChosen!=null){
            binding.spinnerState.setSelection(adap.getPosition(stateChosen))

        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.address1= binding.etAddressLine1.text.toString()
        viewModel.address2= binding.etAddressLine2.text.toString()
        viewModel.city= binding.etCity.text.toString()
        viewModel.zipCode= binding.etZipcode.text.toString()

        viewModel.stateAddress = binding.spinnerState.selectedItem.toString()

    }



    private fun checkLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(requireContext())
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        //Prompt the user once explanation has been shown
                        requestLocationPermission()
                    }
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                requestLocationPermission()
            }
            return false
        } else {
//            checkBackgroundLocation()
            return true
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
    }

    override fun onItemClick(position: Int) {
    }



}

