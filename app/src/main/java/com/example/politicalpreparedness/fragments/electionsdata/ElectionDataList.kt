package com.example.politicalpreparedness.fragments.electionsdata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.databinding.FragmentElectionDataListBinding

class ElectionDataList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentElectionDataListBinding.inflate(inflater)
        binding.lifecycleOwner=this


        return binding.root
    }

}