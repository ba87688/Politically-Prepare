package com.example.politicalpreparedness.viewmodels.currentelection

import android.app.Application
import androidx.lifecycle.*
import com.example.politicalpreparedness.adapters.CurrentElectionAdapter
import com.example.politicalpreparedness.adapters.RepresentativeDataAdapter
import com.example.politicalpreparedness.models.Election
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrentElectionsViewModel @Inject constructor(
    val database: ElectionDatabase, application: Application, val repository:CurrentElectionRepository)
    : AndroidViewModel(application) {

    val currentElections = repository.getCurrentElectionsFromDB().asLiveData()
    val savedElections = repository.getAllSavedElectionsFlow().asLiveData()

    fun update(election: Election){
        viewModelScope.launch {
            repository.update(election)
        }
    }

}