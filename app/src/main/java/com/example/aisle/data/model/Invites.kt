package com.example.aisle.data.model

data class Invites(
    var pending_invitations_count: Int? = 0,
    var profiles: List<Profile>? = listOf(),
    var totalPages: Int? = 0
)