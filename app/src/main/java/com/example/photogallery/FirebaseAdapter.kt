package com.example.photogallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class FirebaseAdapter(private val context: Context) :
    FirebaseRecyclerAdapter<Photo, FirebaseAdapter.PhotoHolder>(
        buildOptions()
    ) {
    private lateinit var mListener: OnItemClickListener

    companion object {
        private fun buildQuery() = FirebaseDatabase.getInstance()
            .reference
            .child("Photo")
            .limitToLast(50)

        private fun buildOptions() = FirebaseRecyclerOptions.Builder<Photo>()
            .setQuery(buildQuery(), Photo::class.java)
            .build()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class PhotoHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTodoTitle)
        val img: ImageView = itemView.findViewById(R.id.photo)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_view, parent, false)

        return PhotoHolder(itemView, mListener)
    }

    override fun onBindViewHolder(
        holder: PhotoHolder, position: Int, model: Photo
    ) {

        holder.title.text = model.title
        holder.img.setBackgroundResource(0)
        FirebaseStorage.getInstance()
            .getReference("images/${model.img}").downloadUrl.addOnSuccessListener {
                Glide.with(context)
                    .load(it) // Uri of the picture
                    .into(holder.img)
            }
    }
}