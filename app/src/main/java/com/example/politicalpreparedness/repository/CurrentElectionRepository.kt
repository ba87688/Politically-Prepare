package com.example.politicalpreparedness.repository

import androidx.lifecycle.LiveData
import com.example.politicalpreparedness.models.Election
import com.example.politicalpreparedness.network.database.CurrentElectionDao
import com.example.politicalpreparedness.network.database.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentElectionRepository @Inject constructor(val dao: CurrentElectionDao):CurrentElectionRepositoryInterface{

    fun getCurrentElectionsFromDB() = networkBoundResource(
        query = { dao.getAllElectionsFlow()},
        fetch = { var elect: ArrayList<Election> = arrayListOf()
                elect
                },
        saveFetchResult = {

        }
    )



    override suspend fun getElections(): Result<List<Election>> {
        TODO("Not yet implemented")
    }


    override suspend fun insert(election: Election) {
        withContext(Dispatchers.IO){
            dao.insert(election)
        }
    }

    override suspend fun delete(election: Election) {
        withContext(Dispatchers.IO){
            dao.delete(election)
        }
    }

    override fun getElectionByID(iid: String): Election {
        return dao.getElectionByID(iid)
    }

    override fun getAllElections(): List<Election> {
        return dao.getAllElections()
    }

    override fun getAllElecions(): LiveData<List<Election>> {
        return dao.getAllElecions()
    }

    override fun getAllElectionsFlow(): Flow<List<Election>> {
        return dao.getAllElectionsFlow()
    }


}