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
import com.kristina.onlineshopapp.data.db.ShopDatabase
import com.kristina.onlineshopapp.data.repository.ProductRepository
import com.kristina.onlineshopapp.domain.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProductInfoViewModel(product: Product, app: Application) : AndroidViewModel(app) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val productRepository =
        ProductRepository(ShopDatabase.getInstance(app).productDao)

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product>
        get() = _selectedProduct

    init {
        _selectedProduct.value = product
    }

    fun setFavorite() {
        uiScope.launch {
            var product = _selectedProduct.value
            product?.favourite = !product?.favourite!!
            _selectedProduct.value = product
            productRepository.updateFavoriteStatus(product)
        }
    }

}