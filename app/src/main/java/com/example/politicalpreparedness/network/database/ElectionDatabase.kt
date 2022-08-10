package com.example.politicalpreparedness.network.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.politicalpreparedness.models.Election

@Database(entities = [Election::class], version = 1, exportSchema = false)
abstract class ElectionDatabase : RoomDatabase(){
    abstract fun currentElectionDao():CurrentElectionDao


    companion object {
        @Volatile
        private var INSTANCE: ElectionDatabase? = null

        fun getDatabase(context: Context): ElectionDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ElectionDatabase::class.java,
                    "elections_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }


    }

}