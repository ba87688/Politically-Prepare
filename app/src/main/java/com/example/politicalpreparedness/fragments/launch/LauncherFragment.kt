package com.example.politicalpreparedness.fragments.launch

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.politicalpreparedness.databinding.FragmentLauncherBinding
import com.example.politicalpreparedness.models.Election
import com.example.politicalpreparedness.models.Elects
import com.example.politicalpreparedness.models.representative.RepresentativesData
import com.example.politicalpreparedness.models.representative.parseRepresentative
import com.example.politicalpreparedness.models.representatives.Representatives
import com.example.politicalpreparedness.network.database.CurrentElectionDao
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.network.retrofit.ElectionsAPI
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import com.example.politicalpreparedness.viewmodels.currentelection.CurrentElectionsViewModel
import com.example.politicalpreparedness.viewmodels.currentelection.CurrentElectionsViewModelFactory
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class LauncherFragment : Fragment() {

    @Inject
    lateinit var retro:ElectionsAPI

    @Inject
    lateinit var db:ElectionDatabase
    @Inject
    lateinit var application:Application
    @Inject
    lateinit var dao:CurrentElectionDao

    @Inject
    lateinit var repo:CurrentElectionRepository

    private lateinit var viewModel: CurrentElectionsViewModel

//    var representativesData:RepresentativesData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLauncherBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory = CurrentElectionsViewModelFactory ( db, application,repo)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentElectionsViewModel::class.java)

        //LIVE DATA


//        viewModel.currentElections.observe(viewLifecycleOwner, Observer { it ->
//            Log.i("TAG", "onCreateView LIVE DATA: ${it.data?.size}")
//            Log.i("TAG", "onCreateView LIVE DATA: ${it.data}")
//
//        })



//        Picasso.get().load("http://bioguide.congress.gov/bioguide/photo/S/S001175.jpg").into(binding.imageView);
//        Glide.with(requireContext()).load("http://bioguide.congress.gov/bioguide/photo/S/S001175.jpg").circleCrop().into(binding.imageView)

        binding.buttonUpcomingElections.setOnClickListener {
            val nav = findNavController()
            nav.navigate(LauncherFragmentDirections.actionLauncherFragmentToElectionDataList())
        }
        binding.buttonFindRepresentatives.setOnClickListener {
            val nav = findNavController()
            nav.navigate(LauncherFragmentDirections.actionLauncherFragmentToFindMyRepresentativeFragment())
        }
        return binding.root
    }
}