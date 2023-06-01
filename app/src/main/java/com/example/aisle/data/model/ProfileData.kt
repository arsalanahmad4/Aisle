package com.example.aisle.data.model

data class ProfileData(
    var invitation_type: String? = "",
    var preferences: List<PreferenceX>? = listOf(),
    var question: String? = ""
)