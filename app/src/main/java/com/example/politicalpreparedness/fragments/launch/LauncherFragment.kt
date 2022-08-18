package com.example.politicalpreparedness.fragments.launch

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.politicalpreparedness.databinding.FragmentLauncherBinding
import com.example.politicalpreparedness.network.database.CurrentElectionDao
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.network.retrofit.ElectionsAPI
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import com.example.politicalpreparedness.viewmodels.launcherscreen.LaunchViewModeo
import com.example.politicalpreparedness.viewmodels.launcherscreen.LaunchViewModeoFactory
import dagger.hilt.android.AndroidEntryPoint
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


//    private val viewModel: LaunchViewModeo by viewModels()
    private lateinit var viewModel: LaunchViewModeo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLauncherBinding.inflate(inflater)
        binding.lifecycleOwner = this


        val viewModelFactory = LaunchViewModeoFactory ( null,this)
//
        viewModel = ViewModelProvider(this, viewModelFactory).get(LaunchViewModeo::class.java)

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