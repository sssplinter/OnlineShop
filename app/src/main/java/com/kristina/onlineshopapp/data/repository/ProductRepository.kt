package com.kristina.onlineshopapp.data.repository

import androidx.lifecycle.LiveData
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
            // трасформируем сущности из бд в представление для UI
            productEntities.map(ProductEntity::toDomain)
        }

    suspend fun fetchProducts() {
        withContext(Dispatchers.IO) {
            refreshProducts()
        }
    }

    suspend fun updateFavoriteStatus(){
        productDao.
    }

    private suspend fun refreshProducts() {
        try {
            // получаем данные из сети и записываем их в бд
            val products = ShopApi.retrofitService.getProducts().map(ProductDto::toDomain)
            updateDatabase(products)
        } catch (exception: Exception) {
           // todo msg no connection Toast.makeText(, text, duration).show()
        }
    }

    private suspend fun updateDatabase(products: List<Product>) {
        products.forEach { product ->
            val id = product.id

            val productEntity = productDao.getProductById(id)

            if (productEntity != null) {
                // если запись уже есть в таблице
                productDao.insertProduct(product.toEntity(productEntity))
            } else {
                // если нет -> добавляем
                productDao.insertProduct(product.toEntity())
            }

        }
    }
}