package com.kristina.onlineshopapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kristina.onlineshopapp.data.db.dao.ProductDao
import com.kristina.onlineshopapp.data.db.entity.ProductEntity
import com.kristina.onlineshopapp.data.network.ShopApi
import com.kristina.onlineshopapp.data.network.model.ProductDto
import com.kristina.onlineshopapp.data.toDomain
import com.kristina.onlineshopapp.data.toEntity
import com.kristina.onlineshopapp.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val productDao: ProductDao) {

    private val _products = productDao.getProducts()
    val products: LiveData<List<Product>>
        get() = Transformations.map(_products) { productEntities ->
            productEntities.map(ProductEntity::toDomain)
        }

    suspend fun fetchProducts() {
        withContext(Dispatchers.IO) {
            refreshProducts()
        }
    }

    private suspend fun refreshProducts() {
        try {
            val products = ShopApi.retrofitService.getProducts().map(ProductDto::toDomain)
            updateDatabase(products)
        } catch (exception: Exception) {
            //ПоКАЗАТЬ СООБЩЕНИЕ ОБ ОШИБКЕ НЕТ СЕТИ СУКА
        }
    }

    private suspend fun updateDatabase(products: List<Product>) {
        products.forEach { product ->
            val id = product.id

            val productEntity = productDao.getProductById(id)

            if (productEntity != null) {
                productDao.insertProduct(product.toEntity(productEntity))
            } else {
                productDao.insertProduct(product.toEntity())
            }

        }
    }
}