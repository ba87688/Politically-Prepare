package com.example.politicalpreparedness.viewmodels.findrepresentative

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindRepresentativeViewModel @Inject constructor(
    val database: ElectionDatabase,
    application: Application,
    val repository: CurrentElectionRepository
    ) : AndroidViewModel(application){

}