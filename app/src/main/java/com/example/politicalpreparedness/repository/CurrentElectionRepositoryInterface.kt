package com.example.politicalpreparedness.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.politicalpreparedness.models.Election
import kotlinx.coroutines.flow.Flow

interface CurrentElectionRepositoryInterface {

    suspend fun getElections(): Result<List<Election>>


    suspend fun insert(election: Election)

    suspend fun delete(election: Election)

    suspend fun getElectionByID(iid:String):Election

    fun getAllElections():List<Election>

    fun getAllElecions(): LiveData<List<Election>>

    fun getAllElectionsFlow(): Flow<List<Election>>


    suspend fun insertElections(elections: List<Election>)

    suspend fun deleteAllElections()


}