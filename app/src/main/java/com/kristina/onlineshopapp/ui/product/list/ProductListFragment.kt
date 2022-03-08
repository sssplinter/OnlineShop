package com.kristina.onlineshopapp.ui.product.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.kristina.onlineshopapp.R
import com.kristina.onlineshopapp.databinding.ProductListFragmentBinding
import com.kristina.onlineshopapp.domain.model.Product

class ProductListFragment : Fragment(), ProductAdapter.ProductRececlerViewItemInterface {

    private var viewModel: ProductListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: ProductListFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.product_list_fragment, container, false
        )

        val viewModelFactory = ProductListViewModelFactory(binding.root.context.applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductListViewModel::class.java)

        val adapter = ProductAdapter(this)
        binding.productList.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel?.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel?.search(newText)
                return false
            }
        })

        binding.slider.addOnChangeListener { slider, value, fromUser ->
            viewModel?.setPriceBound(value)
        }

        viewModel?.products?.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel?.fetchProducts()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel = null
    }

    override fun sendProductToFragment(product: Product) {
        findNavController().navigate(
            R.id.action_productListFragment_to_productInfoFragment, bundleOf("product" to product)
        )
    }

    override fun setProductFavoriteStatus(product: Product) {
        viewModel?.setFavorite(product)
    }

}