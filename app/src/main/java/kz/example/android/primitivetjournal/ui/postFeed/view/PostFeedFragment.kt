package kz.example.android.primitivetjournal.ui.postFeed.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_post_feed.*
import kz.example.android.primitivetjournal.R
import kz.example.android.primitivetjournal.ui.adapters.PostAdapter
import kz.example.android.primitivetjournal.ui.adapters.PostRecListPaginationListener
import kz.example.android.primitivetjournal.ui.adapters.PostRecListPlayerScrollListener
import kz.example.android.primitivetjournal.ui.postFeed.viewModel.PostFeedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PostFeedFragment : Fragment(R.layout.fragment_post_feed) {

    private val postFeedViewModel: PostFeedViewModel by viewModel()
    private val adapter: PostAdapter = PostAdapter(mutableListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recList.layoutManager = LinearLayoutManager(context)
        recList.adapter = adapter
        
        postFeedViewModel.fetchData(offset = adapter.itemCount)
        postFeedViewModel.posts.observe(viewLifecycleOwner, Observer {
            adapter.addItems(it)
        })

        postFeedViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            swipeRefresh.isRefreshing = it && adapter.itemCount == 0
        })

        recList.addOnScrollListener(object: PostRecListPaginationListener(){
            override fun requestNewData() {
                if(postFeedViewModel.isLoading.value != true && !postFeedViewModel.isEmpty){
                    postFeedViewModel.fetchData(offset = adapter.itemCount)
                }
            }
        })

        recList.addOnScrollListener(object: PostRecListPlayerScrollListener(){
            override fun onItemIsFirstVisibleItem(index: Int) {
                adapter.playPlayer(index)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        adapter.releaseAllPlayers()
    }

}