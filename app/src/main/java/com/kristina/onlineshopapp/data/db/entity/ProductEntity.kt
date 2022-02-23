package com.kristina.onlineshopapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
data class ProductEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="id")
    val id: Long,

    @ColumnInfo(name="category")
    val category: String,

    @ColumnInfo(name="title")
    val title: String,

    @ColumnInfo(name="price")
    val price: Double,

    @ColumnInfo(name="image")
    val image: String,

    @ColumnInfo(name="description")
    val description: String,

    @ColumnInfo(name="favourite")
    val favourite: Boolean = false,

    @ColumnInfo(name="comment")
    val comment: String = ""
)

