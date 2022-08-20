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
import com.example.politicalpreparedness.util.Constants.CURRENTFOLLOWSTATE
import com.example.politicalpreparedness.util.Constants.ELECTIONDAY
import com.example.politicalpreparedness.util.Constants.ELECTIONNAME
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

        //build viewmodel
        val viewModelFactory = CurrentElectionsViewModelFactory ( db, application,repo,null, this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentElectionsViewModel::class.java)



        setLinks()


        binding.apply {
            viewModel.state.set(CURRENTFOLLOWSTATE,args.election.saved)
            viewModel.followed = viewModel.getElectionStatus()
            viewModel.setButtonStatus(viewModel.followed)
            buttonFollowElection.text= if (viewModel.followed){
                "Unfollow Election" }else{"Follow Election"}


            viewModel.state.set(ELECTIONNAME,args.election.name)
            viewModel.state.set(ELECTIONDAY,args.election.electionDay)

            tvDateOfElection.text = viewModel.getElectionD()
            tvElectionTitle.text = viewModel.getElectionN()




        }


        viewModel.electionFollowed.observe(viewLifecycleOwner){ status->
            viewModel.followed = status
            binding.buttonFollowElection.text= if (viewModel.followed){
                "Unfollow Election" }else{"Follow Election"}

        }




        binding.buttonFollowElection.setOnClickListener {
            //if saved, unsave
            if(viewModel.followed){
                args.election.saved = false
                val updated = Election(args.election.electionDay,args.election.id,args.election.name,args.election.ocdDivisionId,saved = false)
                viewModel.update(updated)
            }else{
                args.election.saved = true
                val updated = Election(args.election.electionDay,args.election.id,args.election.name,args.election.ocdDivisionId,saved = true)
                viewModel.update(updated)
            }
        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()

    }
    override fun onResume() {
        super.onResume()

    }


    private fun setLinks() {
        binding.link.setMovementMethod(LinkMovementMethod.getInstance())
        binding.link.setLinkTextColor(Color.BLUE)
        binding.link2.setMovementMethod(LinkMovementMethod.getInstance())
        binding.link2.setLinkTextColor(Color.BLUE)

    }

}