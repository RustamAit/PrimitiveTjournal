package kz.example.android.primitivetjournal.repository.user

import io.reactivex.Single
import kz.example.android.primitivetjournal.data.domain.User

interface UserRepository {

    fun getCurrentUser(): Single<User>
    fun setCurrentToken(token: String)
    fun clearAccessToken()
}
