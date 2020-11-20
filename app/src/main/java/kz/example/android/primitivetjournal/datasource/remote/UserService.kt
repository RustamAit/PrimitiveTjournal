package kz.example.android.primitivetjournal.datasource.remote

import io.reactivex.Single
import kz.example.android.primitivetjournal.data.api.ObjectApiResponse
import kz.example.android.primitivetjournal.data.api.UserFromApi
import retrofit2.http.GET

interface UserService{

    @GET("user/me")
    fun getMe(): Single<ObjectApiResponse<UserFromApi>>
}