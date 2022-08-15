package com.example.politicalpreparedness.fragments.electionsdata

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.adapters.CurrentElectionAdapter
import com.example.politicalpreparedness.databinding.FragmentElectionDataListBinding
import com.example.politicalpreparedness.models.Election
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import com.example.politicalpreparedness.viewmodels.currentelection.CurrentElectionsViewModel
import com.example.politicalpreparedness.viewmodels.currentelection.CurrentElectionsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ElectionDataList : Fragment(), CurrentElectionAdapter.OnItemClickListener  {

    private lateinit var viewModel: CurrentElectionsViewModel
    @Inject
    lateinit var db: ElectionDatabase
    @Inject
    lateinit var application: Application
    @Inject
    lateinit var repo: CurrentElectionRepository


    lateinit var list: List<Election>
//    val args: ElectionDataListArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentElectionDataListBinding.inflate(inflater)
        binding.lifecycleOwner=this

        val viewModelFactory = CurrentElectionsViewModelFactory ( db, application,repo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentElectionsViewModel::class.java)

        viewModel.currentElections.observe(viewLifecycleOwner, Observer { it ->

            list = it.data!!
            val adapter = CurrentElectionAdapter(list!!, this)
            binding.rvUpcomingElections.adapter =adapter



        })



//        Log.i("TAG", "next view: ${args.electionList.e}")
//        val adapter = CurrentElectionAdapter(args.electionList.e, this)



        binding.rvUpcomingElections.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onItemClick(position: Int) {


        val selectedElection = list.get(position)
        Log.i("TAG", "onItemClick: ${selectedElection.name}")
//
        val nav = findNavController()
        nav.navigate(ElectionDataListDirections.actionElectionDataListToElectionDataDetail(selectedElection))




    }

}