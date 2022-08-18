package com.example.politicalpreparedness.viewmodels.launcherscreen

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.politicalpreparedness.network.database.ElectionDatabase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class LaunchViewModeo @AssistedInject constructor (
    @Assisted private val state: SavedStateHandle
):ViewModel() {

    init {
        val s = state.get<String>("HELL")
        Log.i("TAG", "the view is $s: ")
    }

    fun setKEYS( savedInstanceState: Bundle?){
        if (savedInstanceState!=null){
            val s = state.get<String>("HELL")
            Log.i("TAG", "the view isww $s: ")

        }
        else{
            Log.i("TAG", "the view was null bro: ")

        }
    }


}