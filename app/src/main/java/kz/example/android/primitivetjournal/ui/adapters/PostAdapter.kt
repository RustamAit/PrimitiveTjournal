package kz.example.android.primitivetjournal.ui.adapters

import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.containsKey
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.item_post.view.*
import kotlinx.android.synthetic.main.item_post_with_image.view.*
import kotlinx.android.synthetic.main.item_video.view.*
import kz.example.android.primitivetjournal.R
import kz.example.android.primitivetjournal.data.domain.Post
import kz.example.android.primitivetjournal.data.domain.PostTypes

class PostAdapter(private val dataset: MutableList<Post>): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var playersMap: SparseArray<SimpleExoPlayer> = SparseArray()
    private var currentPlayer: SimpleExoPlayer? = null

    override fun getItemViewType(position: Int): Int {
        return when(dataset[position].type){
            PostTypes.GIF -> PostViewHolderTypes.WITH_VIDEO
            PostTypes.JPG -> PostViewHolderTypes.WITH_IMAGE
            PostTypes.MP4 -> PostViewHolderTypes.WITH_VIDEO
            else -> PostViewHolderTypes.WITH_VIDEO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return when(viewType){
            PostViewHolderTypes.WITH_VIDEO -> PostWithVideoViewHolder(view)
            PostViewHolderTypes.WITH_IMAGE -> PostWithImageViewHolder(view)
            else -> PostWithVideoViewHolder(view)
        }
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(dataset[position], position)
    }

    fun addItems(items: List<Post>){
        dataset.addAll(items)
        notifyItemRangeInserted(dataset.size, items.size)
    }

    override fun onViewRecycled(holder: PostViewHolder) {
        super.onViewRecycled(holder)
        holder.index?.let { releaseRecycledPlayer(it) }
    }

    fun releaseAllPlayers(){
        playersMap.forEach { key, value ->
            value.release()
        }
    }

    fun releaseRecycledPlayer(index: Int){
        playersMap[index]?.release()
    }



    fun playPlayer(index: Int){
        if(playersMap[index]?.playWhenReady == false){
            pauseCurrentPlayer()
            playersMap.get(index)?.playWhenReady = true
            currentPlayer = playersMap.get(index)
        }
    }

    fun pauseCurrentPlayer(){
        currentPlayer?.let {
            it.playWhenReady = false
        }
    }

    open inner class PostViewHolder(v: View): RecyclerView.ViewHolder(v){
        var index: Int? = null
        open fun bind(p: Post, position: Int){
            index = position
            if(!p.title.isNullOrBlank()){
                itemView.titleTextView?.visibility = View.VISIBLE
                itemView.titleTextView?.text = p.title
            } else {
                itemView.titleTextView?.visibility = View.GONE
            }
        }
    }

    inner class PostWithVideoViewHolder(v: View): PostViewHolder(v){
        override fun bind(p: Post, position: Int){
            super.bind(p, position)
            itemView.videoViewStub?.inflate()
            p.size?.height?.let {
                val layoutParams = itemView.videoContainer.layoutParams
                layoutParams.height = it
                itemView.videoContainer.layoutParams = layoutParams
                val thumbnailLayoutParams = itemView.thumbnailImage.layoutParams
                thumbnailLayoutParams.height = it
                itemView.thumbnailImage.layoutParams = layoutParams
            }

            p.url?.let { loadVideo(itemView.videoContainer, it, position) }
            Glide.with(itemView)
                .load(p.thumbnailUrl)
                .centerCrop()
                .placeholder(R.color.grey)
                .into(itemView.thumbnailImage)
        }

        private fun loadVideo(playerView: PlayerView, url: String, index: Int){
            val player = SimpleExoPlayer.Builder(playerView.context).build().apply {
                playWhenReady = false
                repeatMode = Player.REPEAT_MODE_ALL
                setHandleAudioBecomingNoisy(true)
            }

            playerView.useController = false
            playerView.setKeepContentOnPlayerReset(true)


            val mediaSource = ProgressiveMediaSource.Factory(
                DefaultHttpDataSourceFactory("Demo")
            ).createMediaSource(MediaItem.fromUri(url))

            if(playersMap.containsKey(index)){
                playersMap.remove(index)
            }
            playersMap[index] = player

            player.setMediaSource(mediaSource)
            player.prepare()

            player.addListener(object: Player.EventListener{
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    if(isPlaying){
                        itemView.thumbnailImage?.visibility = View.GONE
                    } else {
                        itemView.thumbnailImage?.visibility = View.VISIBLE
                    }
                }

                override fun onIsLoadingChanged(isLoading: Boolean) {
                    super.onIsLoadingChanged(isLoading)
                    if(isLoading){
                        if(!player.isPlaying && player.playWhenReady){
                            itemView.videoLoader.visibility = View.VISIBLE
                        }
                    } else{
                        itemView.videoLoader.visibility = View.GONE
                    }
                }
            })

            playerView.player = player

        }
    }

    inner class PostWithImageViewHolder(v: View): PostViewHolder(v){
        override fun bind(p: Post, position: Int){
            super.bind(p, position)
            itemView.imageViewStub?.inflate()
            Glide.with(itemView)
                .load(p.url)
                .placeholder(R.color.grey)
                .into(itemView.imageContainer)
            if (p.hasExternalSource){
                itemView.externalSourceRefImageView?.visibility = View.VISIBLE
            }
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Переход на другую прилу :)", Toast.LENGTH_LONG).show()
            }
        }
    }

}

object PostViewHolderTypes{
    const val WITH_IMAGE = 123123
    const val WITH_VIDEO = 343434

}