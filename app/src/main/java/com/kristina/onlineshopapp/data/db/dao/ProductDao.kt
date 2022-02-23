package com.kristina.onlineshopapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kristina.onlineshopapp.data.db.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(product: ProductEntity)

    @Insert
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products_table WHERE id=:productId")
    suspend fun getProductById(productId: Long): ProductEntity?

    @Query("SELECT * FROM products_table")
    fun getProducts() : LiveData<List<ProductEntity>>

    @Query("UPDATE products_table SET favourite = :favorite WHERE id = :productId")
    fun updateFavoriteStatus(productId: Long, favorite: Boolean)
}