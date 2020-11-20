package kz.example.android.primitivetjournal.repository.user

import kz.example.android.primitivetjournal.data.domain.mapToDomain
import kz.example.android.primitivetjournal.datasource.local.LocalSharedPref
import kz.example.android.primitivetjournal.datasource.remote.UserService

class UserRepositoryImpl(private val userService: UserService, private val sharedPref: LocalSharedPref): UserRepository{

    override fun setCurrentToken(token: String) = sharedPref.setAccessToken(token)

    override fun clearAccessToken() = sharedPref.clearAccessToken()

    override fun getCurrentUser() = userService.getMe().map { it.result.mapToDomain() }

}