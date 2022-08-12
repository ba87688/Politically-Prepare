package com.example.politicalpreparedness.fragments.findrepresentative

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.databinding.FragmentFindMyRepresentativeBinding


class FindMyRepresentativeFragment : Fragment() {


    lateinit var binding: FragmentFindMyRepresentativeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFindMyRepresentativeBinding.inflate(inflater)


        return binding.root
    }


}