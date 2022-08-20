package com.example.politicalpreparedness.viewmodels.findrepresentative

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.adapters.RepresentativeDataAdapter
import com.example.politicalpreparedness.models.representative.RepresentativeProfile
import com.example.politicalpreparedness.models.representative.parseRepresentative
import com.example.politicalpreparedness.models.representatives.Representatives
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import com.example.politicalpreparedness.util.Constants
import com.example.politicalpreparedness.util.Constants.ADDRESS1
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class FindRepresentativeViewModel @AssistedInject constructor(
    @Assisted val state: SavedStateHandle,
    val database: ElectionDatabase,
    application: Application,
    val repository: CurrentElectionRepository
) : AndroidViewModel(application) {



    private val _addressOne = MutableLiveData<String>()
    val addressOne: LiveData<String> = _addressOne
    fun setAddressOne(address1: String){
        _addressOne.postValue(address1)
    }


    var address1 = state.get<String>(ADDRESS1)
        set(value) {
            field = value
            state.set(ADDRESS1,value)
        }


    init {
        var s = state.get<String>(ADDRESS1) ?:" hll"
        setAddressOne(s)

    }

    private val _representativesAdapter = MutableLiveData<RepresentativeDataAdapter>()
    val representativesAdapt: LiveData<RepresentativeDataAdapter> = _representativesAdapter

    private val _representativesProfiles = MutableLiveData<List<RepresentativeProfile>>()
    val representativesProfiles: LiveData<List<RepresentativeProfile>> = _representativesProfiles



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


    fun getFirstAddressLine():String{
        address1 = state.get<String>(ADDRESS1)  ?: ""
        return address1.toString()

    }
    fun setFirstAddressLine(address1:String){
        state.set(ADDRESS1,address1)
    }



}