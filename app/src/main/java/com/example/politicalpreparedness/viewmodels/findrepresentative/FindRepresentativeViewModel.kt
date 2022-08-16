package com.example.politicalpreparedness.viewmodels.findrepresentative

import android.app.Application
import androidx.lifecycle.*
import com.example.politicalpreparedness.models.representatives.Representatives
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindRepresentativeViewModel @Inject constructor(
    val database: ElectionDatabase,
    application: Application,
    val repository: CurrentElectionRepository
    ) : AndroidViewModel(application){




        private val _representatives = MutableLiveData<Representatives?>()
        val representatives : LiveData<Representatives?> = _representatives


    fun getRepresentatives(address:String,city:String,state:String,zipcode:String){

        viewModelScope.launch {
            val response = repository.getRepresentatives(address,city,state,zipcode)
            _representatives.postValue(response)
        }
    }























}