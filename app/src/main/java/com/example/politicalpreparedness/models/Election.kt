package com.example.politicalpreparedness.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Election(
    val electionDay: String,
    val id: String,
    val name: String,
    val ocdDivisionId: String
):Parcelable

@Parcelize
data class Elects(
    val e: List<Election>
):Parcelable