package com.example.politicalpreparedness.models.representatives

data class Representatives(
    val normalizedInput: NormalizedInput,
    val officials: List<Official>
)