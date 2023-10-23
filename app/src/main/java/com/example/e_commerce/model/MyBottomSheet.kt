package com.example.e_commerce.model

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.example.e_commerce.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

import kotlin.math.roundToInt

@SuppressLint("SetTextI18n")
class MyBottomSheet(context: Context, private val product: Product, private val bottomSheetInterface: BottomSheetInterface) : BottomSheetDialog(context) {
    private var count:Int = 1
    init {
        val view = LayoutInflater.from(context).inflate(R
            .layout.add_to_cart_bottom_sheet, null)
        val title: TextView = view.findViewById(R.id.bottom_sheet_title)
        val brand: TextView = view.findViewById(R.id.bottom_sheet_brand)
        val rating: TextView = view.findViewById(R.id.bottom_sheet_rating)
        val price: TextView = view.findViewById(R.id.bottom_sheet_price)
        val priceTotal: TextView = view.findViewById(R.id.bottom_sheet_price_total)
        val quantity: TextView = view.findViewById(R.id.bottom_sheet_quantity)

        val plus: MaterialButton = view.findViewById(R.id.bottom_sheet_plus)
        val minus: MaterialButton = view.findViewById(R.id.bottom_sheet_minus)
        val add: MaterialButton = view.findViewById(R.id.bottom_sheet_add_mb)

        title.text = product.title
        brand.text = product.brand
        rating.text = ((product.rating * 10).roundToInt().toDouble() / 10).toString()
        price.text = "${product.price} $"
        priceTotal.text = "${product.price} $"
        plus.setOnClickListener {
            count += 1
            quantity.text = count.toString()
            priceTotal.text = (product.price * count).toString() + " $"
        }
        minus.setOnClickListener {
            if (count == 1) return@setOnClickListener
            count -= 1
            quantity.text = count.toString()
            priceTotal.text = (product.price * count).toString() + " $"
        }

        add.setOnClickListener {
            bottomSheetInterface.onAdd(product, count)
            this.dismiss()
        }

        this.setContentView(view)
        this.show()
    }
    interface BottomSheetInterface{
        fun onAdd(product: Product, quantity:Int)
    }
}