package com.example.politicalpreparedness.fragments.electiondatadetails

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.databinding.FragmentElectionDataDetailBinding
import com.example.politicalpreparedness.models.Election
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import com.example.politicalpreparedness.viewmodels.currentelection.CurrentElectionsViewModel
import com.example.politicalpreparedness.viewmodels.currentelection.CurrentElectionsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint

class ElectionDataDetail : Fragment() {
    val args : ElectionDataDetailArgs by navArgs()

    private lateinit var viewModel: CurrentElectionsViewModel
    @Inject
    lateinit var db: ElectionDatabase
    @Inject
    lateinit var application: Application
    @Inject
    lateinit var repo: CurrentElectionRepository


    lateinit var binding: FragmentElectionDataDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentElectionDataDetailBinding.inflate(inflater)
        binding.lifecycleOwner=this

        val viewModelFactory = CurrentElectionsViewModelFactory ( db, application,repo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentElectionsViewModel::class.java)


        binding.tvDateOfElection.text = args.election.electionDay
        binding.tvElectionTitle.text = args.election.name



        setLinks()


        val status = args.election.saved==true
        if (status){
            binding.buttonFollowElection.text="Unfollow Election"

        }else{
            binding.buttonFollowElection.text="Follow Election"

        }

        binding.buttonFollowElection.setOnClickListener {
            Log.i("TAG", "onCreateView: before saved ${args.election} ")
            Log.i("TAG", "onCreateView: after saved ${args.election}")
            //if saved, unsave
            if(args.election.saved==true){
                lifecycleScope.launch {

                    withContext(Dispatchers.IO) {
                        val updated = Election(args.election.electionDay,args.election.id,args.election.name,args.election.ocdDivisionId,saved = false)

                        db.currentElectionDao().update(updated)

                    }}

                binding.buttonFollowElection.text="Follow Election"

            }else{
                lifecycleScope.launch {

                    withContext(Dispatchers.IO) {
                        val updated = Election(args.election.electionDay,args.election.id,args.election.name,args.election.ocdDivisionId,saved = true)
                        Log.i("TAG", "onCreateView: before saved list ${db.currentElectionDao().getAllElections()}")
                        db.currentElectionDao().update(updated)
                        Log.i("TAG", "onCreateView: after saved list ${db.currentElectionDao().getAllElections()}")
                        Log.i("TAG", "onCreateView: after all saved list ${db.currentElectionDao().getAllSavedElections()}")


                    }
                }
                binding.buttonFollowElection.text="Unfollow Election"


            }




        }
        return binding.root
    }


    private fun setLinks() {
        binding.link.setMovementMethod(LinkMovementMethod.getInstance())
        binding.link.setLinkTextColor(Color.BLUE)
        binding.link2.setMovementMethod(LinkMovementMethod.getInstance())
        binding.link2.setLinkTextColor(Color.BLUE)

    }

}