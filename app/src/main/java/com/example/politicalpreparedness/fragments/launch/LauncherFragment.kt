package com.example.politicalpreparedness.fragments.launch

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.politicalpreparedness.databinding.FragmentLauncherBinding
import com.example.politicalpreparedness.models.Election
import com.example.politicalpreparedness.models.Elects
import com.example.politicalpreparedness.models.representatives.Representatives
import com.example.politicalpreparedness.network.database.CurrentElectionDao
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.network.retrofit.ElectionsAPI
import com.example.politicalpreparedness.viewmodels.currentelection.CurrentElectionsViewModel
import com.example.politicalpreparedness.viewmodels.currentelection.CurrentElectionsViewModelFactory
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

    private lateinit var viewModel: CurrentElectionsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLauncherBinding.inflate(inflater)
        binding.lifecycleOwner = this


        val viewModelFactory = CurrentElectionsViewModelFactory ( db, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentElectionsViewModel::class.java)

        val evan = viewModel.evan
        Log.i("TAG", "onCreateView: Evan is $evan")


























        var list1 = mutableListOf<Representatives>()
        var e1: Representatives? = null
//        val retro = RetrofitInstance.api
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val i = retro.getRepresentatives()
                val body = i.body()


                e1 = i.body()
                Log.i("man o man", "testing representatives data: list ${body?.offices?.get(0)?.name}")

                withContext(Dispatchers.Main){

                }
            }
        }






























        //testing retrofit

        var list = mutableListOf<Election>()
        var e:Elects? = null
//        val retro = RetrofitInstance.api
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val i = retro.getElection()
                val body = i.body()?.elections
                list = body?.toMutableList()!!

                Log.i("Ridiculous", "onCreateView: list ${list.toString()}")
                Log.i("TAG", "onCreateView success: ${i} ")
                Log.i("TAG", "onCreateView success: ${i.isSuccessful} ")
                if (i.body() != null) {
                    Log.i("TAG", "onCreateView not null : ${i.body()} ")

                }
                withContext(Dispatchers.Main){
                    e = Elects(body)
                }
            }
        }

        Log.i("TAG", "onCreateView weeee: ${e} ")

        binding.buttonUpcomingElections.setOnClickListener {
            val nav = findNavController()

            nav.navigate(LauncherFragmentDirections.actionLauncherFragmentToElectionDataList(e!!))
//            nav.navigate(LauncherFragmentDirections.actionLauncherFragmentToTestCollapseFragment())
        }

        binding.buttonFindRepresentatives.setOnClickListener {
            val nav = findNavController()

//            nav.navigate(LauncherFragmentDirections.actionLauncherFragmentToFindMyRepresentativeFragment(e1!!))
        }


        return binding.root
    }


}