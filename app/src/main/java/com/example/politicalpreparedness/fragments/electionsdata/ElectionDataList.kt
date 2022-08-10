package com.example.politicalpreparedness.fragments.electionsdata

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.adapters.CurrentElectionAdapter
import com.example.politicalpreparedness.databinding.FragmentElectionDataListBinding

class ElectionDataList : Fragment() {


    val args: ElectionDataListArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentElectionDataListBinding.inflate(inflater)
        binding.lifecycleOwner=this

        Log.i("TAG", "next view: ${args.electionList.e}")
        val adapter = CurrentElectionAdapter(args.electionList.e)

        binding.rvUpcomingElections.adapter =adapter
        binding.rvUpcomingElections.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

}