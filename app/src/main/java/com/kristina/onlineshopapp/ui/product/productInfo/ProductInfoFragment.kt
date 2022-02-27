package com.kristina.onlineshopapp.ui.product.productInfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kristina.onlineshopapp.R

class ProductInfoFragment : Fragment() {

    companion object {
        fun newInstance() = ProductInfoFragment()
    }

    private lateinit var viewModel: ProductInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}