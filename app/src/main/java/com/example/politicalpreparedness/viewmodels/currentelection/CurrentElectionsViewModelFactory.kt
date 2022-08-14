package com.example.politicalpreparedness.viewmodels.currentelection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject


class CurrentElectionsViewModelFactory @Inject constructor(
    private val dataSource: ElectionDatabase,
    private val application: Application,
    private val repo: CurrentElectionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CurrentElectionsViewModel::class.java)) {
            return CurrentElectionsViewModel(dataSource, application,repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }


}