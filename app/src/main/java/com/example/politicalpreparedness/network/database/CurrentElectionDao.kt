package com.example.politicalpreparedness.network.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.politicalpreparedness.models.Election
import kotlinx.coroutines.flow.Flow


@Dao
interface CurrentElectionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(election: Election)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertElections(elections: List<Election>)

    @Delete
    suspend fun delete(election: Election)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(election: Election)

    @Query("DELETE FROM current_elections")
    suspend fun deleteAllElections()

    @Query("SELECT * FROM current_elections")
    fun getAllElectionsFlow(): Flow<List<Election>>

    @Query("SELECT * FROM current_elections WHERE saved =1 ")
    fun getAllSavedElectionsFlow():Flow<List<Election>>

    @Query("SELECT * FROM current_elections WHERE saved =0 ")
    fun getAllUnSavedElections():List<Election>
    @Query("SELECT * FROM current_elections WHERE saved =1 ")
    fun getAllSavedElections2():List<Election>


    @Query("SELECT * FROM current_elections WHERE :iid = id")
    fun getElectionByID(iid:String):Election


    @Query("SELECT * FROM current_elections")
    fun getAllElections():List<Election>

    @Query("SELECT * FROM current_elections")
    fun getAllElecions(): LiveData<List<Election>>


















}