package com.example.politicalpreparedness.models.representative

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepresentativeProfile (
    val office:String,
    val name: String,
    val party:String,
    val photo: String?,
    val twitter: String?,
    val facebook: String?,
    val website: String?
):Parcelable

@Parcelize
data class RepresentativesData(
    val e:List<RepresentativeProfile>
):Parcelable