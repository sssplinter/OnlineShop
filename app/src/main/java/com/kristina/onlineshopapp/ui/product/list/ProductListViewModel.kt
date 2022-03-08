package com.kristina.onlineshopapp.ui.product.list

import android.content.Context
import androidx.lifecycle.*
import com.kristina.onlineshopapp.data.db.ShopDatabase
import com.kristina.onlineshopapp.data.repository.ProductRepository
import com.kristina.onlineshopapp.domain.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProductListViewModel(context: Context) : ViewModel() {

    private val productRepository =
        ProductRepository(ShopDatabase.getInstance(context).productDao)

    private var _products = productRepository.products

    private val query = MutableLiveData<String>("")

    val products = MediatorLiveData<List<Product>>().apply{
        fun filter(products : List<Product>?, query: String?){
            if (products != null && query != null) {
                val filteredProducts = products.filter { product ->
                    product.title.startsWith(query, true)
                }
                value = filteredProducts
            }
        }

        addSource(query){
            filter(_products.value, it)
        }

        addSource(_products){
            filter(it, query.value)
        }
    }


    fun fetchProducts() {
        viewModelScope.launch {
            productRepository.fetchProducts()
        }
    }

    fun setFavorite(product: Product) {
        viewModelScope.launch {
            product.favourite = !product.favourite
            productRepository.updateProduct(product)
        }
    }

    fun search(newQuery: String) {
        query.value = newQuery
    }

}