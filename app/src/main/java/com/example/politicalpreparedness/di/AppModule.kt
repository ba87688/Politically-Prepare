package com.example.politicalpreparedness.di

import android.app.Application
import com.example.politicalpreparedness.network.database.CurrentElectionDao
import com.example.politicalpreparedness.network.database.ElectionDatabase
import com.example.politicalpreparedness.network.retrofit.ElectionsAPI
import com.example.politicalpreparedness.network.retrofit.RetrofitInstance
import com.example.politicalpreparedness.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofitInstance(
    ): ElectionsAPI =
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ElectionsAPI::class.java)

    @Singleton
    @Provides
    fun provideElectionDatabase(
        context:Application
    ): ElectionDatabase = ElectionDatabase.getDatabase(context)


    @Provides
    @Singleton
    fun provideCurrentElectionDao(
        db:ElectionDatabase
    ): CurrentElectionDao = db.currentElectionDao()
}