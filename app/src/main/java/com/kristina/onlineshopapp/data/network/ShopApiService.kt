package com.kristina.onlineshopapp.data.network

import com.kristina.onlineshopapp.data.network.model.ProductDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://fakestoreapi.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ShopApiService{
    @GET("products")
    suspend fun getProducts() : List<ProductDto>
}

object ShopApi{
    val retrofitService: ShopApiService by lazy {
        retrofit.create(ShopApiService::class.java)
    }
}
