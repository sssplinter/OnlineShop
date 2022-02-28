package com.kristina.onlineshopapp.ui.product.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.kristina.onlineshopapp.R
import com.kristina.onlineshopapp.databinding.ProductListFragmentBinding
import com.kristina.onlineshopapp.domain.model.Product

class ProductListFragment : Fragment(), ProductAdapter.OnClick {

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

        viewModel!!.navigateToSelectedProduct.observe(viewLifecycleOwner) {
            if (null != it) {
//                this.findNavController().navigate(
//                   // ProductListFragmentDirections.actionProductListFragmentToProductInfoFragment
//                )actionProductListFragmentToProductInfoFragment
                viewModel?.displayProductInfoComplete()
            }
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

    override fun sendData(product: Product) {
        findNavController().navigate(
            R.id.action_productListFragment_to_productInfoFragment, bundleOf("product" to product)
        )
        Log.i("Click", "Click from fragment")
    }
}