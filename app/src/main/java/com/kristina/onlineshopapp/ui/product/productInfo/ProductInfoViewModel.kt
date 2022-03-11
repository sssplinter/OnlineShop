package com.kristina.onlineshopapp.ui.product.productInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kristina.onlineshopapp.data.db.ShopDatabase
import com.kristina.onlineshopapp.data.repository.ProductRepository
import com.kristina.onlineshopapp.domain.model.Product
import kotlinx.coroutines.launch

class ProductInfoViewModel(product: Product, app: Application) : AndroidViewModel(app) {

    private val productRepository =
        ProductRepository(ShopDatabase.getInstance(app).productDao)

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product>
        get() = _selectedProduct

    init {
        _selectedProduct.value = product
    }

    fun setFavorite() {
       viewModelScope.launch {
            val product = _selectedProduct.value
            product?.favourite = !product?.favourite!!
            _selectedProduct.value = product
            productRepository.updateProduct(product)
        }
    }

}