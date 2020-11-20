package kz.example.android.primitivetjournal.ui.profile

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import kz.example.android.primitivetjournal.core.utills.BaseStatefulViewModel
import kz.example.android.primitivetjournal.data.domain.User
import kz.example.android.primitivetjournal.repository.user.UserRepository

class UserViewModel(private val userRepository: UserRepository): BaseStatefulViewModel(){

    val currentUser: MutableLiveData<User?> = MutableLiveData()

    fun fetchCurrentUser(){
        isLoading.value = true
        userRepository.getCurrentUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.value = false
                currentUser.value = it
            },{
                isLoading.value = false
                isError.value = false
                currentUser.value = null
            })
            .let { compositeDisposable.add(it) }
    }

    fun setCurrentUserAccessToken(token: String){
        userRepository.setCurrentToken(token)
    }

    fun signOut(){
        userRepository.clearAccessToken()
        currentUser.value = null
    }

}