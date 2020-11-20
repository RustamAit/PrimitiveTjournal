package kz.example.android.primitivetjournal.ui.postFeed.viewModel

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import kz.example.android.primitivetjournal.core.utills.BaseStatefulViewModel
import kz.example.android.primitivetjournal.data.api.PostFromApi
import kz.example.android.primitivetjournal.data.domain.Post
import kz.example.android.primitivetjournal.repository.feed.PostRepository

class PostFeedViewModel(private val postRepository: PostRepository): BaseStatefulViewModel() {

    val posts: MutableLiveData<List<Post>> = MutableLiveData()
    var isEmpty: Boolean = false

    fun fetchData(count: Int=20, offset: Int = 0){
        isLoading.value = true
        postRepository.getPostFeed(count, offset)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ response ->
                isLoading.value = false
                posts.value = response
                isEmpty = response.isEmpty()
            }.let { compositeDisposable.add(it) }

    }


}