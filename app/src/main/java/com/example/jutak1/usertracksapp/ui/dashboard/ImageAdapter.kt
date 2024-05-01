package com.example.jutak1.usertracksapp.ui.dashboard

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.jutak1.usertracksapp.R
import java.io.File

class ImageAdapter(private val imageFiles: List<File>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageFile = imageFiles[position]
        holder.imageView.setImageURI(Uri.fromFile(imageFile))
    }

    override fun getItemCount(): Int {
        return imageFiles.size
    }
}