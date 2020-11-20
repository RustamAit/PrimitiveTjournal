package kz.example.android.primitivetjournal.datasource.remote

import io.reactivex.Single
import kz.example.android.primitivetjournal.data.api.ListApiResponse
import kz.example.android.primitivetjournal.data.api.PostFromApi
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("subsite/237832/timeline/new")
    fun getSubsiteFeed(@Query("count") count: Int, @Query("offset") offset: Int): Single<ListApiResponse<PostFromApi>>

}