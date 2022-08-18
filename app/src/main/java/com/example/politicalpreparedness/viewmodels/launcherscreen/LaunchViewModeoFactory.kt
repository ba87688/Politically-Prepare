package com.example.politicalpreparedness.viewmodels.launcherscreen

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import java.lang.IllegalArgumentException

class LaunchViewModeoFactory(
    defaultArgs: Bundle? = null,
    savedStateRegistryOwner: SavedStateRegistryOwner
):AbstractSavedStateViewModelFactory(savedStateRegistryOwner,defaultArgs){
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(LaunchViewModeo::class.java)) {
            return LaunchViewModeo(handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")     }

}
