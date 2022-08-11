package com.example.politicalpreparedness.viewmodels.currentelection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.politicalpreparedness.network.database.ElectionDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrentElectionsViewModel @Inject constructor(val database: ElectionDatabase, application: Application): AndroidViewModel(application) {

    lateinit var evan:String

    init {
        evan ="Evan"
    }


}