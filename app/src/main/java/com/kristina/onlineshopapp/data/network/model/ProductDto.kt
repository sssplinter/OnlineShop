package com.kristina.onlineshopapp.data.network.model

data class ProductDto(
    val id: Long,
    val category: String,
    val title: String,
    val price: Double,
    val image: String,
    val description: String
)