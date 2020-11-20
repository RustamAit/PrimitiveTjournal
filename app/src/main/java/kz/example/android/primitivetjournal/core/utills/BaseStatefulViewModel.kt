package kz.example.android.primitivetjournal.core.utills

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseStatefulViewModel(): ViewModel(){

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isError: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}