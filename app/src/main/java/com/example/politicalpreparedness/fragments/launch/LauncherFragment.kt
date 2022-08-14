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

    var representativesData:RepresentativesData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLauncherBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory = CurrentElectionsViewModelFactory ( db, application,repo)

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

                representativesData = RepresentativesData(parseRepresentative(e1!!))

                Log.i("TAG", "the king has returned: $representativesData")
                Log.i("TAG", "the king has returned: ${representativesData.toString()}")


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
                dao.insertElections(list)
//                for(election in list){
//                    dao.insert(election)
//                }

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


        //LIVE DATA


        viewModel.currentElections.observe(viewLifecycleOwner, Observer { it ->
            Log.i("TAG", "onCreateView LIVE DATA: ${it.data?.size}")
            Log.i("TAG", "onCreateView LIVE DATA: ${it.data}")

        })

































        binding.buttonUpcomingElections.setOnClickListener {
            val nav = findNavController()

            nav.navigate(LauncherFragmentDirections.actionLauncherFragmentToElectionDataList(e!!))
//            nav.navigate(LauncherFragmentDirections.actionLauncherFragmentToTestCollapseFragment())
        }

        binding.buttonFindRepresentatives.setOnClickListener {
            val nav = findNavController()

            nav.navigate(LauncherFragmentDirections.actionLauncherFragmentToFindMyRepresentativeFragment(representativesData!!))
        }


        return binding.root
    }


}