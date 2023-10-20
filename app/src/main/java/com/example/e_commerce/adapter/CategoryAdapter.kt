package com.example.e_commerce.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.google.android.material.card.MaterialCardView


class CategoryAdapter(
    private val categories: List<String>,
    private val context: Context,
    val categoryRecyclerView: RecyclerView,
    private val categoryPressed: CategoryPressed
) : RecyclerView.Adapter<CategoryAdapter.MyHolder>() {
    var current = 0

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: MaterialCardView = itemView.findViewById(R.id.category_mcv)
        val text: TextView = itemView.findViewById(R.id.category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return categories.size + 1
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if (position == 0) {
            holder.text.text = "All"
        } else {
            holder.text.text = categories[position - 1].capitalize()
        }
        if (current == position) {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.blue))
            holder.text.setTextColor(context.resources.getColor(R.color.white))
        } else {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.white))
            holder.text.setTextColor(context.resources.getColor(R.color.blue))
        }
        holder.cardView.setOnClickListener {
            if (position != current) {
                notifyItemChanged(current)
                current = position
                notifyItemChanged(current)
                if (position == 0) categoryPressed.onPressed("")
                else categoryPressed.onPressed(categories[position - 1])
            }
            categoryRecyclerView.visibility = View.GONE
        }
    }

    interface CategoryPressed {
        fun onPressed(category: String)
    }

}