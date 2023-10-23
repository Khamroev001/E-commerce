package com.example.e_commerce.model

data class CartData(
    val carts: List<Cart>,
    val limit: Int,
    val skip: Int,
    val total: Int
)