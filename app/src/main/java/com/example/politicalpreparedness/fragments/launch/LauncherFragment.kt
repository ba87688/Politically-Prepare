package com.example.politicalpreparedness.fragments.launch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.databinding.FragmentLauncherBinding


class LauncherFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLauncherBinding.inflate(inflater)
        binding.lifecycleOwner=this



        binding.buttonUpcomingElections.setOnClickListener {
            val nav = findNavController()
            nav.navigate(LauncherFragmentDirections.actionLauncherFragmentToElectionDataList())
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}