package com.example.politicalpreparedness.network.database

import androidx.room.*
import com.example.politicalpreparedness.models.Election

@Dao
interface CurrentElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(election: Election)

    @Delete
    fun delete(election: Election)

    @Query("SELECT * FROM current_elections WHERE :iid = id")
    fun getElectionByID(iid:String):Election


    @Query("SELECT * FROM current_elections")
    fun getAllElections():List<Election>
}