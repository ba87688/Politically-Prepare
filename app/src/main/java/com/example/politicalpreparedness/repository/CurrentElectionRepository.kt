package com.example.politicalpreparedness.repository

import androidx.lifecycle.LiveData
import com.example.politicalpreparedness.models.Election
import com.example.politicalpreparedness.models.representatives.Representatives
import com.example.politicalpreparedness.network.database.CurrentElectionDao
import com.example.politicalpreparedness.network.database.networkBoundResource
import com.example.politicalpreparedness.network.retrofit.ElectionsAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentElectionRepository @Inject constructor(
    val dao: CurrentElectionDao,
    val retrofit: ElectionsAPI
) : CurrentElectionRepositoryInterface {

    fun getCurrentElectionsFromDB() = networkBoundResource(
        query = { dao.getAllElectionsFlow() },
        fetch = {
            withContext(Dispatchers.IO) {
                retrofit.getElection().body()?.elections
            }
        },
        saveFetchResult = { electionsList ->
//                deleteAllElections()
                insertElections(electionsList!!)

        }
    )

//    suspend fun getRepresentatives(address:String,city:String,state:String,zipcode:String):Representatives?{
//
//        val request = retrofit.getRepresentatives(address,city,state,zipcode)
//        if (request.isSuccessful){
//            return request.body()
//        }
//
//        return null
//
//    }

    suspend fun getRepresentativesViaAddress(address:String):Representatives?{

        val request = retrofit.getRepresentativesViaAddress(address)
        if (request.isSuccessful){
            return request.body()
        }

        return null

    }

    override suspend fun getElections(): Result<List<Election>> {
        TODO("Not yet implemented")
    }

    fun getAllSavedElectionsFlow():Flow<List<Election>>{
        return dao.getAllSavedElectionsFlow()
    }


    override suspend fun insert(election: Election) {
        withContext(Dispatchers.IO) {
            dao.insert(election)
        }
    }

    override suspend fun delete(election: Election) {
        withContext(Dispatchers.IO) {
            dao.delete(election)
        }
    }

    override suspend fun getElectionByID(iid: String): Election {
        val election:Election
        withContext(Dispatchers.IO){
            election= dao.getElectionByID(iid)
        }
        return election
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
    override suspend fun insertElections(elections: List<Election>) {
        withContext(Dispatchers.IO) {

            dao.insertElections(elections)
        }
    }

    override suspend fun deleteAllElections() {
        withContext(Dispatchers.IO) {

            dao.deleteAllElections()
        }
    }

    suspend fun update(election: Election){
        withContext(Dispatchers.IO) {
            dao.update(election)

        }

    }



}