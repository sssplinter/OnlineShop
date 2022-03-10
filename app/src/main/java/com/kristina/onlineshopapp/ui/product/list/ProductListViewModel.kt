package com.kristina.onlineshopapp.ui.product.list

import android.content.Context
import androidx.lifecycle.*
import com.kristina.onlineshopapp.currency.CurrencyParser
import com.kristina.onlineshopapp.data.db.ShopDatabase
import com.kristina.onlineshopapp.data.repository.ProductRepository
import com.kristina.onlineshopapp.domain.model.Product
import kotlinx.coroutines.launch

class ProductListViewModel(context: Context) : ViewModel() {

    private val productRepository =
        ProductRepository(ShopDatabase.getInstance(context).productDao)

    private var _products = productRepository.products

    private val query = MutableLiveData<String>("")

    private val priceBound = MutableLiveData<Float>(1000.0F)

    lateinit var data: HashMap<String, Pair<Double, Double>>

    val products = MediatorLiveData<List<Product>>().apply {

        fun filter(products: List<Product>?, query: String?, price: Float?) {
            if (products != null && query != null) {
                val filteredProducts = products.filter { product ->
                    (product.title.startsWith(query, true)
                            || product.category.startsWith(query, true))
                            && (product.price < price!! || price <= 0)
                }
                value = filteredProducts
            }
        }

        addSource(_products) {
            filter(it, query.value, priceBound.value)
        }

        addSource(query) {
            filter(_products.value, it, priceBound.value)
        }

        addSource(priceBound) {
            filter(_products.value, query.value, it)
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

    fun setPriceBound(newPrice: Float) {
        priceBound.value = newPrice
    }

}