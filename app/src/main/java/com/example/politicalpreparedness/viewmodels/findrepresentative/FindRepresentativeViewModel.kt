package com.example.politicalpreparedness.viewmodels.findrepresentative

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.politicalpreparedness.adapters.RepresentativeDataAdapter
import com.example.politicalpreparedness.models.representative.RepresentativeProfile
import com.example.politicalpreparedness.models.representative.parseRepresentative
import com.example.politicalpreparedness.models.representatives.Representatives
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FindRepresentativeViewModel @AssistedInject constructor(
    @Assisted val state: SavedStateHandle,
    val database: ElectionDatabase,
    application: Application,
    val repository: CurrentElectionRepository
) : AndroidViewModel(application) {

    //    val savedData = state.get()
    lateinit var ev: String

    init {

        ev = "jackie"
        state.set("HI", "LOSER")
        ev = state.get<String>("HI") ?: "Idiot"
    }


    private val _representativesAdapter = MutableLiveData<RepresentativeDataAdapter>()
    val representativesAdapt: LiveData<RepresentativeDataAdapter> = _representativesAdapter

    private val _representativesProfiles = MutableLiveData<List<RepresentativeProfile>>()
    val representativesProfiles: LiveData<List<RepresentativeProfile>> = _representativesProfiles

//
//    fun getRepresentativeProfiles(address: String, city: String, state: String, zipcode: String){
//        var adapter = RepresentativeDataAdapter(mutableListOf())
//        viewModelScope.launch {
//
//            val response = repository.getRepresentatives(address, city, state, zipcode)
//            if (response!=null) {
//                val rep = parseRepresentative(response)
//                _representativesProfiles.postValue(rep.toList())
//                Log.i("TAG", "getRepresentativeProfiles crazy right: ${rep.toString()}")
//            }
//        }
//
//    }

    fun getRepresentativesViaAddress(address: String) {
        var adapter = RepresentativeDataAdapter(mutableListOf())
        viewModelScope.launch {

            val response = repository.getRepresentativesViaAddress(address)
            if (response != null) {
                val rep = parseRepresentative(response)
                _representativesProfiles.postValue(rep.toList())
                Log.i("TAG", "getRepresentativeProfiles crazy right: ${rep.toString()}")
            }
        }

    }

    fun getRepAdapter() {
        val list = _representativesProfiles.value?.toList()
        _representativesAdapter.postValue(RepresentativeDataAdapter(list!!))

    }


}