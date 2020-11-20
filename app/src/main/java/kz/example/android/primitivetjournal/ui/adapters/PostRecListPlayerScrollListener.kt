package kz.example.android.primitivetjournal.ui.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PostRecListPlayerScrollListener : RecyclerView.OnScrollListener(){

    private var firstVisibleItem = 0
    private var visibleItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val  manager = (recyclerView.layoutManager as? LinearLayoutManager)
        visibleItemCount = manager?.childCount ?: 0
        firstVisibleItem = manager?.findFirstCompletelyVisibleItemPosition() ?: 0
        onItemIsFirstVisibleItem(firstVisibleItem)

    }

    abstract fun onItemIsFirstVisibleItem(index: Int)

}