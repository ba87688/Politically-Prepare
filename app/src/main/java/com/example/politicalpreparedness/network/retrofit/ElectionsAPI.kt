package com.example.politicalpreparedness.network.retrofit

import com.example.politicalpreparedness.models.Elections
import com.example.politicalpreparedness.models.representatives.Representatives
import com.example.politicalpreparedness.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ElectionsAPI {
    @GET("/civicinfo/v2/elections")
    suspend fun getElection(
            @Query("key") apiKey: String = "$API_KEY"
    ): Response<Elections>


    @GET("/civicinfo/v2/representatives")
    suspend fun getRepresentatives(
        @Query("address") address: String = "28166 st louise dr",
        @Query("city") city: String = "warren",
        @Query("state") state: String = "MI",
        @Query("zipcode") zipcode: String = "48092",
        @Query("key") apiKey: String = "$API_KEY"

    ) : Response<Representatives>


}