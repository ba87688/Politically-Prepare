package com.example.politicalpreparedness.di

import com.example.politicalpreparedness.network.database.CurrentElectionDao
import com.example.politicalpreparedness.network.retrofit.ElectionsAPI
import com.example.politicalpreparedness.repository.CurrentElectionRepository
import com.example.politicalpreparedness.repository.CurrentElectionRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCurrentElectionRepository(
        dao: CurrentElectionDao, retrofit: ElectionsAPI

    ): CurrentElectionRepositoryInterface {
        return CurrentElectionRepository(dao,retrofit)
    }


}