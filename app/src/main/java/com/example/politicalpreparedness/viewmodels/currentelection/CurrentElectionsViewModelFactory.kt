package com.example.politicalpreparedness.viewmodels.currentelection

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject


class CurrentElectionsViewModelFactory @Inject constructor(
    private val dataSource: ElectionDatabase,
    private val application: Application,
    private val repo: CurrentElectionRepository,
    defaultArgs: Bundle? = null,
    savedStateRegistryOwner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner,defaultArgs) {







    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(CurrentElectionsViewModel::class.java)) {
            return CurrentElectionsViewModel(handle,dataSource, application,repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")    }


}