package com.dailiusprograming.posts_app.ui.postslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dailiusprograming.posts_app.R
import com.dailiusprograming.posts_app.data.model.Posts

class PostsListAdapter(private val onClick: (Posts) -> Unit) :
    RecyclerView.Adapter<PostsListAdapter.ViewHolder>() {

    private var postsList = emptyList<Posts>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycleview_post_item, parent, false
        )
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(postsList[position])
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    class ViewHolder(
        itemView: View,
        val onClick: (Posts) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        val textTitle: TextView = itemView.findViewById(R.id.txtTitle)
        private var currentPost: Posts? = null

        init {
            itemView.setOnClickListener {
                currentPost?.let {
                    onClick(it)
                }
            }
        }

        fun bind(posts: Posts) {
            currentPost = posts
            textTitle.text = posts.title
        }
    }

    fun setData(posts: List<Posts>) {
        this.postsList = posts
        notifyDataSetChanged()
    }
}
