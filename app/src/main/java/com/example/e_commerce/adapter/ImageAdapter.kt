package com.example.e_commerce.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.e_commerce.R

class ImageAdapter(val images:List<String>, val viewPager2: ViewPager2, val constraintLayout: ConstraintLayout): RecyclerView.Adapter<ImageAdapter.MyHolder>() {
    inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.image.load(images[position])
        holder.itemView.setOnClickListener {
            viewPager2.updateLayoutParams { height = ViewGroup.LayoutParams.MATCH_PARENT}
            constraintLayout.setBackgroundColor(Color.BLACK)
        }
    }
}
