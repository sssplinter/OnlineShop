package com.kristina.onlineshopapp.domain.model

data class Product(
    val id: Long,
    val category: String,
    val title: String,
    val price: Double,
    val image: String,
    val description: String,
    val favourite: Boolean = false,
    val comment: String = ""
)