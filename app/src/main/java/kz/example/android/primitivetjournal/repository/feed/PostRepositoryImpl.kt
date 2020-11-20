package kz.example.android.primitivetjournal.repository.feed

import kz.example.android.primitivetjournal.data.domain.mapToDomain
import kz.example.android.primitivetjournal.datasource.remote.PostService

class PostRepositoryImpl(private val postService: PostService): PostRepository {

    override fun getPostFeed(count: Int, offset: Int) = postService.getSubsiteFeed(count, offset).map { it.result.map { it.mapToDomain() } }

}


