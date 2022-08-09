package com.example.politicalpreparedness.network.retrofit

import com.example.politicalpreparedness.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api:ElectionsAPI by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ElectionsAPI::class.java)
    }
}