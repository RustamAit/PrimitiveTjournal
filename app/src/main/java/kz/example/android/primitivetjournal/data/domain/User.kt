package kz.example.android.primitivetjournal.data.domain

import kz.example.android.primitivetjournal.data.api.UserFromApi

data class User(
    val id: Int,
    val name: String,
    val avatarUrl: String
)

fun UserFromApi.mapToDomain() = User(
    this.id,
    this.name,
    this.avatar_url
)