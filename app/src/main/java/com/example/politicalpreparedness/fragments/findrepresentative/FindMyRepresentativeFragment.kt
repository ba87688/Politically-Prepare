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
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.databinding.FragmentFindMyRepresentativeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

val MY_PERMISSIONS_REQUEST_LOCATION = 0

class FindMyRepresentativeFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var otherList:ArrayList<String>


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
            val snackbar = Snackbar
                .make(
                    requireActivity(),
                    requireView(),
                    "You need to give permission to find your location",
                    Snackbar.LENGTH_LONG
                )
            snackbar.show()
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
                            Log.i("ZIP CODE", "onCreateView: $address0 ")
                            Log.i("ZIP CODE", "onCreateView: $address1 ")
                            Log.i("ZIP CODE", "onCreateView: $city ")

                            binding.etAddressLine1.setText(address0)
                            binding.etAddressLine2.setText(address1)
                            binding.etCity.setText(city)
                            binding.spinnerState
                            binding.etZipcode.setText(zipcode)

                            val c = otherList.indexOf(state)
                            Log.i("TAG", "onCreateView: state $state")
                            Log.i("TAG", "onCreateView: state $c")
                            binding.spinnerState.setSelection(c)

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


}
