package com.example.politicalpreparedness.network.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.politicalpreparedness.models.Election

@Dao
interface CurrentElectionDao {

    @Insert
    fun insert(election: Election)

    @Delete
    fun delete(election: Election)

    @Query("SELECT * FROM current_elections WHERE :iid = id")
    fun getElectionByID(iid:String):Election


    @Query("SELECT * FROM current_elections")
    fun getAllElections():List<Election>
}