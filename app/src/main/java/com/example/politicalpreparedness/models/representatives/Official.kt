package com.example.politicalpreparedness.models.representatives

data class Official(
    val address: List<Addres>,
    val channels: List<Channel>,
    val emails: List<String>,
    val name: String,
    val party: String,
    val phones: List<String>,
    val photoUrl: String,
    val urls: List<String>
)