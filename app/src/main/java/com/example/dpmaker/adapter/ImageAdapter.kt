package com.example.dpmaker.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dpmaker.R

class ImageAdapter(
    private val mContext: Context,
    private val mUploads: MutableList<String?>
) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        val v: View =
            LayoutInflater.from(mContext).inflate(R.layout.layout_images, parent, false)
        return ImageViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: ImageViewHolder,
        position: Int
    ) {
        Log.i("uploads", " --------muploads---------"+mUploads)
        val uploadCurrent = mUploads[position]
        if (uploadCurrent != null) {
//            holder.textViewName.text = uploadCurrent.name
//            Log.i("uploads", " -------uploadcurrent.name----------"+uploadCurrent.name)
            Glide.with(mContext)
                .load(uploadCurrent)
                .into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return mUploads.size
    }

    inner class ImageViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
//        var textViewName: TextView = itemView.findViewById(R.id.textViewName)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)

    }

}