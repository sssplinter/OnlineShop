package com.kristina.onlineshopapp.ui.product.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kristina.onlineshopapp.data.db.ShopDatabase
import com.kristina.onlineshopapp.data.repository.ProductRepository
import com.kristina.onlineshopapp.domain.model.Product
import kotlinx.coroutines.launch

class ProductListViewModel(context: Context) : ViewModel(){

    private val productRepository =
        ProductRepository(ShopDatabase.getInstance(context).productDao)

    val products = productRepository.products


    private val _navigateToSelectedProduct = MutableLiveData<Product>()
    val navigateToSelectedProduct: LiveData<Product>
    get() = _navigateToSelectedProduct

    fun fetchProducts() {
        viewModelScope.launch {
            productRepository.fetchProducts()
        }
    }

    fun displayProductInfo(product: Product){
        _navigateToSelectedProduct.value = product
    }

    fun displayProductInfoComplete(){
        _navigateToSelectedProduct.value = null
    }
}