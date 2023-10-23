package com.example.e_commerce.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.model.ProductX


class  CartProductsAdapter(private val products : List<ProductX>) : RecyclerView.Adapter<CartProductsAdapter.MyHandler>() {
    inner class MyHandler(itemView: View): RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.cart_item_title)
        val price : TextView = itemView.findViewById(R.id.cart_item_price)
        val quantity : TextView = itemView.findViewById(R.id.cart_item_quantity)
        val totalPrice : TextView = itemView.findViewById(R.id.cart_item_total_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHandler {
        return MyHandler(LayoutInflater.from(parent.context).inflate(R.layout.cart_product_item, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHandler, position: Int) {
        val product = products[position]
        holder.title.text = product.title
        holder.price.text = product.price.toString() + " $"
        holder.quantity.text = product.quantity.toString()
        holder.totalPrice.text = product.total.toString() + " $"
    }
}