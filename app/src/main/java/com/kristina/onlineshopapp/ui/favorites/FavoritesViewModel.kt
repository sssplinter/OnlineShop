package com.kristina.onlineshopapp.ui.favorites

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kristina.onlineshopapp.data.db.ShopDatabase
import com.kristina.onlineshopapp.data.repository.ProductRepository
import com.kristina.onlineshopapp.domain.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoritesViewModel(context: Context) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val productRepository =
        ProductRepository(ShopDatabase.getInstance(context).productDao)

    val favorites = productRepository.favorites


    fun setFavorite(product: Product) {
        uiScope.launch {
            product.favourite = !product.favourite
            productRepository.updateFavoriteStatus(product)
        }
    }
}