package com.example.politicalpreparedness.viewmodels.findrepresentative

import android.app.Application
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import dagger.assisted.Assisted
import java.lang.IllegalArgumentException
import javax.inject.Inject

class FindRepresentativeViewModelFactory @Inject constructor(
    private val dataSource: ElectionDatabase,
    private val application: Application,
    private val repo: CurrentElectionRepository
) : AbstractSavedStateViewModelFactory() {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(FindRepresentativeViewModel::class.java)) {
            return FindRepresentativeViewModel(handle,dataSource,application,repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")    }
}