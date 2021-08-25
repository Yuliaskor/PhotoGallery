package com.example.photogallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainAdapter(private val photoList: ArrayList<Photo>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_view, parent, false)

        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val photo: Photo = photoList[position]

        holder.title.text = photo.title
        // holder.img.text = photo.img
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTodoTitle)
        // val img: ImageView = itemView.findViewById(R.id.photo)
    }
}
