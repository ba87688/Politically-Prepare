package com.example.politicalpreparedness.viewmodels.currentelection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrentElectionsViewModel @Inject constructor(
    val database: ElectionDatabase, application: Application, val repository:CurrentElectionRepository)
    : AndroidViewModel(application) {

    lateinit var evan:String

    val currentElections = repository.getCurrentElectionsFromDB().asLiveData()


    init {
        evan ="Evan"
    }

}