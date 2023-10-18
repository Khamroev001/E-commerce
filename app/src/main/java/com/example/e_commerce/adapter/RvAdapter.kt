package com.example.e_commerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.e_commerce.R
import com.example.e_commerce.model.Product

class RvAdapter(var context: Context, var products: List<Product>) : RecyclerView.Adapter<RvAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.img)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val favBtn: ImageView = itemView.findViewById(R.id.favBtn)
        val plusBtn: ConstraintLayout = itemView.findViewById(R.id.plusBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var product: Product = products[position]

        holder.img.load(product.thumbnail)
        holder.name.text = product.title
        holder.price.text = "$ ${product.price}"

//        holder.favBtn.setOnClickListener {
//            product.isFavourite = !product.isFavourite
//
//            if (sneaker.isFavourite) {
//                holder.favBtn.setBackgroundResource(R.drawable.fav)
//            }
//            else {
//                holder.favBtn.setBackgroundResource((R.drawable.fav_border))
//            }
//        }

//        if (sneaker.isFavourite) {
//            holder.favBtn.setBackgroundResource(R.drawable.fav)
//        }
//        else {
//            holder.favBtn.setBackgroundResource((R.drawable.fav_border))
//        }

        holder.plusBtn.setOnClickListener {
//            product.isAddedToCart = true
            Toast.makeText(context, "Savatchaga qo'shildi", Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnClickListener {
//            var intent = Intent(context, SneakerDetail::class.java)
//            intent.putExtra("sneakers", sneakers as java.io.Serializable)
//            intent.putExtra("index", position)
//            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}