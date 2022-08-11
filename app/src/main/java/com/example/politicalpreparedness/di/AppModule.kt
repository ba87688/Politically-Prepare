package com.example.politicalpreparedness.di

import com.example.politicalpreparedness.network.retrofit.ElectionsAPI
import com.example.politicalpreparedness.network.retrofit.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofitInstance(

    ): ElectionsAPI = RetrofitInstance.api

}