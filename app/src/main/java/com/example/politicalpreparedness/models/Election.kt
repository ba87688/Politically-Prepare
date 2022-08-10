package com.example.politicalpreparedness.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "current_elections")
data class Election(
    val electionDay: String,
    @PrimaryKey
    val id: String,
    val name: String,
    val ocdDivisionId: String
):Parcelable

@Parcelize
data class Elects(
    val e: List<Election>
):Parcelable