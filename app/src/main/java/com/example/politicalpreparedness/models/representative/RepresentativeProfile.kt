package com.example.politicalpreparedness.models.representative

data class RepresentativeProfile (
    val office:String,
    val name: String,
    val party:String,
    val photo: String?,
    val twitter: String?,
    val facebook: String?,
    val website: String?

)