package com.example.politicalpreparedness.fragments.findrepresentative

import android.Manifest
import android.app.Activity
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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.adapters.CurrentElectionAdapter
import com.example.politicalpreparedness.adapters.RepresentativeDataAdapter
import com.example.politicalpreparedness.databinding.FragmentFindMyRepresentativeBinding
import com.example.politicalpreparedness.models.representative.parseRepresentative
import com.example.politicalpreparedness.network.database.CurrentElectionDao
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.network.retrofit.ElectionsAPI
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import com.example.politicalpreparedness.viewmodels.findrepresentative.FindRepresentativeViewModel
import com.example.politicalpreparedness.viewmodels.findrepresentative.FindRepresentativeViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

val MY_PERMISSIONS_REQUEST_LOCATION = 0

@AndroidEntryPoint
class FindMyRepresentativeFragment : Fragment(), CurrentElectionAdapter.OnItemClickListener {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var otherList:ArrayList<String>

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

    lateinit var binding: FragmentFindMyRepresentativeBinding

    private lateinit var viewModel:FindRepresentativeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindMyRepresentativeBinding.inflate(inflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())


        val viewModelFactory = FindRepresentativeViewModelFactory(db,application,repo)

        viewModel = ViewModelProvider(this,viewModelFactory).get(FindRepresentativeViewModel::class.java)


        binding.nested.requestDisallowInterceptTouchEvent(true)



        otherList = ArrayList<String>(Arrays.asList(*resources.getStringArray(R.array.states)))

        val spinner = binding.spinnerState

        val c = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.states,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
                spinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
                    AdapterView.OnItemSelectedListener {
                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    }override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    }override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
            }


        //FIND MY REPRESENTATIVE BUTTON via address typed
        binding.buttonFindMyRepresentative.setOnClickListener {
            val allFilled: Boolean = (binding.etAddressLine1.text.isEmpty() || binding.etAddressLine2.text.isEmpty() || binding.etCity.text.isEmpty()|| binding.etZipcode.text.isEmpty())
            if(allFilled){
                Snackbar.make( requireActivity(), requireView(),
                        "You need to fill out all fileds.",
                        Snackbar.LENGTH_LONG).show()
            }else {
                val address1 = binding.etAddressLine1.text.toString()
                val address2 =  binding.etAddressLine2.text.toString()
                val city = binding.etCity.text.toString()
                val zipCode =  binding.etZipcode.text.toString()
                val state = binding.spinnerState.selectedItem.toString()
                viewModel.getRepresentatives(address1.plus(address2),city,zipCode,state)

                //OBSERVE LIVE DATA
                viewModel.representatives.observe(viewLifecycleOwner){ response->
                    if(response==null){
                        Snackbar.make( requireActivity(), requireView(),
                            "network issue.",
                            Snackbar.LENGTH_LONG).show()

                    }else{
                        val rep = parseRepresentative(response)
                        val adapter = RepresentativeDataAdapter(rep.toList())

                        binding.recyclerviewRepresentatives.adapter =adapter
                        binding.recyclerviewRepresentatives.layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }
        }

        //USE MY LOCATION BUTTON - finds rep via location
        binding.buttonUseMyLocation.setOnClickListener {

            checkLocationPermission()
            val c = checkLocationPermission()

            if (c == true) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
//                        Log.i("LOOL", "onCreateView:${location.longitude} ")
                        if (location != null) {
                            Log.i("ZIP CODE", "onCreateView: ${location.longitude} ")

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

                            viewModel.getRepresentatives(address0.plus(address1),city,state,zipcode)
                            //OBSERVE LIVE DATA
                            viewModel.representatives.observe(viewLifecycleOwner){ response->
                                if(response==null){
                                    Snackbar.make( requireActivity(), requireView(),
                                            "network issue.",
                                            Snackbar.LENGTH_LONG).show()

                                }else{
                                    val rep = parseRepresentative(response)
                                    val adapter = RepresentativeDataAdapter(rep.toList())

                                    binding.recyclerviewRepresentatives.adapter =adapter
                                    binding.recyclerviewRepresentatives.layoutManager = LinearLayoutManager(requireContext())
                                }
                            }
                        }
                    }
            }

        }

        return binding.root
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
        TODO("Not yet implemented")
    }


}
