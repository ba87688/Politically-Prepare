package com.example.politicalpreparedness.fragments.electiondatadetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.databinding.FragmentElectionDataDetailBinding


class ElectionDataDetail : Fragment() {
    val args : ElectionDataDetailArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentElectionDataDetailBinding.inflate(inflater)
        binding.lifecycleOwner=this


        return binding.root
    }

}