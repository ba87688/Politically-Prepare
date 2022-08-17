package com.example.politicalpreparedness.fragments.electionsdata

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.politicalpreparedness.adapters.CurrentElectionAdapter
import com.example.politicalpreparedness.adapters.SavedElectionAdapter
import com.example.politicalpreparedness.databinding.FragmentElectionDataListBinding
import com.example.politicalpreparedness.models.Election
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import com.example.politicalpreparedness.viewmodels.currentelection.CurrentElectionsViewModel
import com.example.politicalpreparedness.viewmodels.currentelection.CurrentElectionsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ElectionDataList : Fragment(), CurrentElectionAdapter.OnItemClickListener,
    SavedElectionAdapter.OnItemClickListener {

    private lateinit var viewModel: CurrentElectionsViewModel

    @Inject
    lateinit var db: ElectionDatabase

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var repo: CurrentElectionRepository


    lateinit var list: List<Election>
    lateinit var saved: List<Election>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentElectionDataListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, CurrentElectionsViewModelFactory(db, application, repo))
                .get(CurrentElectionsViewModel::class.java)

        //CURRENT elections
        viewModel.currentElections.observe(viewLifecycleOwner, Observer { it ->
            val data = it.data
            if(data!=null){
                list=data
                val currentElectionAdapter = CurrentElectionAdapter(data, this)
                binding.rvUpcomingElections.adapter = currentElectionAdapter
            }
        })

        //SAVED elections
        viewModel.savedElections.observe(viewLifecycleOwner, Observer { it ->
            val data = it
            if (data != null) {
                saved=data
                val savedElectionAdapter = SavedElectionAdapter(data, this)
                binding.rvCurrentElections.adapter = savedElectionAdapter
            }
        })


        binding.rvCurrentElections.layoutManager = LinearLayoutManager(requireContext())

        binding.rvUpcomingElections.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onItemClick(position: Int) {
        val selectedElection = list.get(position)
        val nav = findNavController()
        nav.navigate(
            ElectionDataListDirections.actionElectionDataListToElectionDataDetail(
                selectedElection
            )
        )

    }

    override fun onItemClick2(position: Int) {
        val selectedElection = saved.get(position)
        val nav = findNavController()
        nav.navigate(
            ElectionDataListDirections.actionElectionDataListToElectionDataDetail(
                selectedElection
            )
        )


    }

}