package com.example.politicalpreparedness.fragments.findrepresentative

import android.Manifest
import android.app.Activity
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.adapters.CurrentElectionAdapter
import com.example.politicalpreparedness.adapters.RepresentativeDataAdapter
import com.example.politicalpreparedness.databinding.FragmentFindMyRepresentativeBinding
import com.example.politicalpreparedness.models.representative.parseRepresentative
import com.example.politicalpreparedness.network.retrofit.ElectionsAPI
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


//    val args: FindMyRepresentativeFragmentArgs by navArgs()

    lateinit var binding: FragmentFindMyRepresentativeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindMyRepresentativeBinding.inflate(inflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())




















        otherList = ArrayList<String>(Arrays.asList(*resources.getStringArray(R.array.states)))

        val spinner = binding.spinnerState

        val c = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.states,
            android.R.layout.simple_spinner_item
        )
            .also { adapter ->

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
                spinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
                    AdapterView.OnItemSelectedListener {
                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        Log.i("TAG", "onItemSelected: ind $position ")
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }
            }



        binding.buttonFindMyRepresentative.setOnClickListener {
            val allFilled: Boolean = (binding.etAddressLine1.text.isEmpty() ||
            binding.etAddressLine2.text.isEmpty() || binding.etCity.text.isEmpty()||
            binding.etZipcode.text.isEmpty())
            if(allFilled){
                val snackbar = Snackbar
                    .make( requireActivity(), requireView(),
                        "You need to fill out all fileds.",
                        Snackbar.LENGTH_LONG
                    )
                snackbar.show()


            }else {
                val address1 = binding.etAddressLine1.text.toString()
                val address2 =  binding.etAddressLine2.text.toString()
                val city = binding.etCity.text.toString()
                val zipCode =  binding.etZipcode.text.toString()
                val state = binding.spinnerState.selectedItem.toString()

                lifecycleScope.launch {
                    withContext(Dispatchers.IO){
                         val repData =retro.getRepresentatives(address1.plus(address2),city,state,zipCode)
                         val rep = parseRepresentative(repData.body()!!)

                        withContext(Dispatchers.Main){
                            Log.i("TAG", "onCreateView: ${rep.toString()}")
                        }
                    }
                }


            }
        }
        binding.buttonUseMyLocation.setOnClickListener {

            checkLocationPermission()
            val c = checkLocationPermission()
            Log.i("Messa", "onCreateView: ${c}")

            if (c == true) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        Log.i("LOOL", "onCreateView:${location.longitude} ")
                        if (location != null) {
                            Log.i("ZIP CODE", "onCreateView: ${location.longitude} ")

                            // use your location object
                            // get latitude , longitude and other info from this
                            val geocoder: Geocoder
                            val addresses: List<Address>
                            geocoder = Geocoder(requireContext(), Locale.getDefault())
                            addresses =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)

                            val address = addresses.get(0).getAddressLine(0)
                            val address0 = addresses.get(0).subThoroughfare
                            val address1 = addresses.get(0).thoroughfare
                            val city = addresses.get(0).locality
                            val state = addresses.get(0).adminArea
                            val country = addresses.get(0).countryName
                            val zipcode = addresses.get(0).postalCode
                            Log.i("ZIP CODE", "onCreateView: $zipcode ")
                            Log.i("ZIP CODE", "onCreateView: $address ")
                            Log.i("ZIP CODE", "onCreateView0: $address0 ")
                            Log.i("ZIP CODE", "onCreateView1: $address1 ")
                            Log.i("ZIP CODE", "onCreateView: $city ")
                            Log.i("ZIP CODE", "onCreateView: $state ")


                            val c = otherList.indexOf(state)
                            Log.i("TAG", "onCreateView: state $state")
                            Log.i("TAG", "onCreateView: state $c")
                            binding.spinnerState.setSelection(c)




                            lifecycleScope.launch {
                                withContext(Dispatchers.IO){
                                    val repData =retro.getRepresentatives(address0.plus(address1),city,state,zipcode)
                                    Log.i("TAG", "onCreateView in s: ${repData.body()?.officials?.size}")

                                    val rep = parseRepresentative(repData.body()!!)

                                    withContext(Dispatchers.Main){
                                        Log.i("TAG", "onCreateView in s: ${rep.toString()}")
                                        Log.i("TAG", "onCreateView in s: ${rep.size}")
                                        val adapter = RepresentativeDataAdapter(rep.toList())

                                        binding.recyclerviewRepresentatives.adapter =adapter
                                        binding.recyclerviewRepresentatives.layoutManager = LinearLayoutManager(requireContext())


                                    }
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
