package com.example.politicalpreparedness.viewmodels.currentelection

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.politicalpreparedness.adapters.CurrentElectionAdapter
import com.example.politicalpreparedness.adapters.RepresentativeDataAdapter
import com.example.politicalpreparedness.models.Election
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentElectionsViewModel @AssistedInject constructor(
    @Assisted val state: SavedStateHandle,
    val database: ElectionDatabase, application: Application, val repository:CurrentElectionRepository)
    : AndroidViewModel(application) {

    lateinit var george:String
    init {
        state.set("YAHOO","GERMANY")
        val name = state.get<String>("YAHOO") ?: "ALBANIA"
        Log.i("TAG", "the key country is : $name ")
    }

    val currentElections = repository.getCurrentElectionsFromDB().asLiveData()
    val savedElections = repository.getAllSavedElectionsFlow().asLiveData()

    fun update(election: Election){
        viewModelScope.launch {
            repository.update(election)
        }
    }

}