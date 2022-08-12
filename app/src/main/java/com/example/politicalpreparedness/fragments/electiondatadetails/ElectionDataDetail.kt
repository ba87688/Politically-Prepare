package com.example.politicalpreparedness.fragments.electiondatadetails

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.databinding.FragmentElectionDataDetailBinding


class ElectionDataDetail : Fragment() {
    val args : ElectionDataDetailArgs by navArgs()

    lateinit var binding: FragmentElectionDataDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentElectionDataDetailBinding.inflate(inflater)
        binding.lifecycleOwner=this


        setLinks()

        return binding.root
    }


    private fun setLinks() {
        binding.link.setMovementMethod(LinkMovementMethod.getInstance())
        binding.link.setLinkTextColor(Color.BLUE)
        binding.link2.setMovementMethod(LinkMovementMethod.getInstance())
        binding.link2.setLinkTextColor(Color.BLUE)

    }

}