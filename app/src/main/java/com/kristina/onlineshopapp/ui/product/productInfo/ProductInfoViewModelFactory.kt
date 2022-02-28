package com.kristina.onlineshopapp.ui.product.productInfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kristina.onlineshopapp.domain.model.Product

class ProductInfoViewModelFactory(
    private val product: Product,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductInfoViewModel::class.java)) {
            return ProductInfoViewModel(product, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}