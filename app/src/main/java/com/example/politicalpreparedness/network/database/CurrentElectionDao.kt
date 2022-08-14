package com.example.politicalpreparedness.network.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.politicalpreparedness.models.Election
import kotlinx.coroutines.flow.Flow


@Dao
interface CurrentElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(election: Election)

    @Delete
    suspend fun delete(election: Election)

    @Query("SELECT * FROM current_elections WHERE :iid = id")
    fun getElectionByID(iid:String):Election


    @Query("SELECT * FROM current_elections")
    fun getAllElections():List<Election>

    @Query("SELECT * FROM current_elections")
    fun getAllElecions(): LiveData<List<Election>>

    @Query("SELECT * FROM current_elections")
    fun getAllElectionsFlow(): Flow<List<Election>>

}