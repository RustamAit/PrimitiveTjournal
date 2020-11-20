package kz.example.android.primitivetjournal.repository.feed

import io.reactivex.Single
import kz.example.android.primitivetjournal.data.api.ListApiResponse
import kz.example.android.primitivetjournal.data.api.PostFromApi
import kz.example.android.primitivetjournal.data.domain.Post

interface PostRepository {

    fun getPostFeed(count: Int, offset: Int): Single<List<Post>>
}
