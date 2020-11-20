package kz.example.android.primitivetjournal.data.domain

import kz.example.android.primitivetjournal.data.api.PostFromApi
import kz.example.android.primitivetjournal.data.api.Size

data class Post(
    val id: Int,
    val title: String,
    val type: PostTypes?,
    val url: String?,
    val size: Size?,
    val thumbnailUrl: String?,
    val hasExternalSource: Boolean
)

fun PostFromApi.mapToDomain() = Post(
    this.id,
    this.title,
    this.cover?.additionalData?.type?.mapToPostType(),
    this.cover?.url,
    this.cover?.size,
    this.cover?.thumbnailUrl,
    this.hasExternalSource()
)
