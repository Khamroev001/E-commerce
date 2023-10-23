package com.example.e_commerce.networking

import com.example.e_commerce.model.CartData
import com.example.e_commerce.model.Login
import com.example.e_commerce.model.Product
import com.example.e_commerce.model.ProductData
import com.example.e_commerce.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {

    @GET("/products")
    fun getAllProducts(): Call<ProductData>

    @GET("/products/{id}")
    fun getProduct(@Path("id") id: Int): Call<Product>


    @GET("/products/search")
    fun search(@Query("q") query : String): Call<ProductData>


    @POST("/auth/login")
    fun search(@Body login: Login): Call<User>


    @POST("/auth/login")
    fun login(@Body login: Login): Call<User>

    @GET("/products/categories")
    fun getCategories(): Call<List<String>>

    @GET("/products/category/{category}")
    fun getByCategory(@Path("category") category : String): Call<ProductData>
    @GET("/carts/user/{id}")
    fun getCartsOfUser(@Path("id") id : Int): Call<CartData>



}