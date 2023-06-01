package com.example.aisle.data.model

data class Likes(
    var can_see_profile: Boolean? = false,
    var likes_received_count: Int? = 0,
    var profiles: List<ProfileX>? = listOf()
)