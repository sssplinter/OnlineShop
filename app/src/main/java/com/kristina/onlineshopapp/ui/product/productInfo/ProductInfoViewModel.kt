package com.kristina.onlineshopapp.ui.product.productInfo

import android.app.Application
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kristina.onlineshopapp.R
import com.kristina.onlineshopapp.domain.model.Product

class ProductInfoViewModel(product: Product, app: Application) : AndroidViewModel(app) {

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product>
        get() = _selectedProduct

    init {
        _selectedProduct.value = product
    }


}