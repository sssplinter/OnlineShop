package com.kristina.onlineshopapp.ui.product.list

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kristina.onlineshopapp.data.db.ShopDatabase
import com.kristina.onlineshopapp.data.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductListViewModel(context: Context) : ViewModel(){

    private val productRepository =
        ProductRepository(ShopDatabase.getInstance(context).productDao)

    val products = productRepository.products

    fun fetchProducts() {
        viewModelScope.launch {
            productRepository.fetchProducts()
        }
    }
}