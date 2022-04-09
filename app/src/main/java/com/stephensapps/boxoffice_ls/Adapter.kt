package com.stephensapps.boxoffice_ls

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val postList:ArrayList<Post>):RecyclerView.Adapter<Adapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: Adapter.PostViewHolder, position: Int) {

        val post : Post = postList[position]
        holder.movieName.text = "Movie: " + post.movieName
        holder.movieRating.text = "Rating: " + post.movieRating + "/100"
        holder.movieDescription.text = "Comments: " + post.description


    }

    override fun getItemCount(): Int {

        return postList.size

    }

    public class PostViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val movieName : TextView = itemView.findViewById(R.id.movieNameText)
        val movieRating : TextView = itemView.findViewById(R.id.movieRatingText)
        val movieDescription : TextView = itemView.findViewById(R.id.movieDescriptionText)

    }
}