package com.example.aisle.ui.dashboard.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aisle.R
import com.example.aisle.data.model.ProfileX
import jp.wasabeef.glide.transformations.BlurTransformation

class LikesAdapter(private val mData: List<ProfileX>) : RecyclerView.Adapter<LikesAdapter.LikesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.like_list_item, parent, false)
        return LikesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LikesViewHolder, position: Int) {
        val data = mData[position]
        holder.bindData(data)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class LikesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView
        private val imageView: ImageView

        init {
            titleTextView = itemView.findViewById<TextView>(R.id.tvLikeProfileName)
            imageView = itemView.findViewById<ImageView>(R.id.ivProfileImage)
        }

        fun bindData(data: ProfileX) {
            titleTextView.text = data.first_name

            Glide.with(itemView.context)
                .load(data.avatar)
                .apply(RequestOptions.bitmapTransform( BlurTransformation(25, 3)))
                .into(imageView)
        }
    }
}