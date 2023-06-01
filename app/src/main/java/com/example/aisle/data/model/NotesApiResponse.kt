package com.example.aisle.data.model

data class NotesApiResponse(
    var invites: Invites? = Invites(),
    var likes: Likes? = Likes()
)