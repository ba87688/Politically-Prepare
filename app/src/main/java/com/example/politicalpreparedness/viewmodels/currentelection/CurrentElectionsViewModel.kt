package com.example.politicalpreparedness.viewmodels.currentelection

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistry
import com.example.politicalpreparedness.adapters.CurrentElectionAdapter
import com.example.politicalpreparedness.adapters.RepresentativeDataAdapter
import com.example.politicalpreparedness.models.Election
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import com.example.politicalpreparedness.util.Constants.CURRENTFOLLOWSTATE
import com.example.politicalpreparedness.util.Constants.ELECTIONDAY
import com.example.politicalpreparedness.util.Constants.ELECTIONNAME
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentElectionsViewModel @AssistedInject constructor(
    @Assisted val state: SavedStateHandle,
    val database: ElectionDatabase,
    application: Application, val repository: CurrentElectionRepository
) : AndroidViewModel(application) {


    private val _electionFollowed = MutableLiveData<Boolean>()
    val electionFollowed: LiveData<Boolean> = _electionFollowed


    var followed: Boolean=false
        set(value) {
            field = value
            state.set(CURRENTFOLLOWSTATE,value)
        }
    var electionName = state.get<String>(ELECTIONNAME)
        set(value) {
            field = value
            state.set(ELECTIONNAME,value)
        }
    var electionDay = state.get<String>(ELECTIONDAY)
        set(value) {
            field = value
            state.set(ELECTIONDAY, value)
        }

    init {

    }

    val currentElections = repository.getCurrentElectionsFromDB().asLiveData()
    val savedElections = repository.getAllSavedElectionsFlow().asLiveData()

    fun update(election: Election) {
        viewModelScope.launch {
            repository.update(election)
        }
        setButtonStatus(election.saved)
    }


    fun setButtonStatus(status:Boolean){
        _electionFollowed.postValue(status)
    }
    fun getElectionN(): String {
        electionName = state.get<String>(ELECTIONNAME) ?: ""
        return electionName.toString()
    }
    fun getElectionD(): String {
        electionDay = state.get<String>(ELECTIONDAY) ?: ""
        return electionDay.toString()
    }
    fun getElectionStatus(): Boolean {
        followed = state.get<Boolean>(CURRENTFOLLOWSTATE) ?: false
        return followed
    }




}