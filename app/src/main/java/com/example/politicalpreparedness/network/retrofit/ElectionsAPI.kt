package com.example.politicalpreparedness.network.retrofit

import com.example.politicalpreparedness.models.Election
import com.example.politicalpreparedness.models.Elections
import com.example.politicalpreparedness.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ElectionsAPI {
    @GET("/civicinfo/v2/elections")
    suspend fun getElection(
            @Query("key") apiKey: String = "$API_KEY"
    ): Response<Elections>

}