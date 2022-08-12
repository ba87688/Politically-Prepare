package com.example.politicalpreparedness.models.representatives

data class GeocodingSummary(
    val addressUnderstood: Boolean,
    val featureId: FeatureId,
    val featureType: String,
    val positionPrecisionMeters: Double,
    val queryString: String
)