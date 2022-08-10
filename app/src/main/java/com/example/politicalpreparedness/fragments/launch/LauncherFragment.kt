package com.example.politicalpreparedness.fragments.launch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.databinding.FragmentLauncherBinding
import com.example.politicalpreparedness.models.Election
import com.example.politicalpreparedness.models.Elects
import com.example.politicalpreparedness.network.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LauncherFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLauncherBinding.inflate(inflater)
        binding.lifecycleOwner = this




        //testing retrofit

        var list = mutableListOf<Election>()
        var e:Elects? = null
        val retro = RetrofitInstance.api
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
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}