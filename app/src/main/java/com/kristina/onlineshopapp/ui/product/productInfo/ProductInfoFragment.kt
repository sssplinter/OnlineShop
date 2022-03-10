package com.kristina.onlineshopapp.ui.product.productInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kristina.onlineshopapp.R
import com.kristina.onlineshopapp.databinding.ProductInfoFragmentBinding
import com.kristina.onlineshopapp.domain.model.Product
import com.kristina.onlineshopapp.utils.OPEN_SOURCE_ERROR
import com.kristina.onlineshopapp.utils.PRODUCT
import com.kristina.onlineshopapp.utils.isOnline

class ProductInfoFragment : Fragment() {

    lateinit var binding: ProductInfoFragmentBinding
    private lateinit var viewModel: ProductInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ProductInfoFragmentBinding.inflate(inflater, container, false)
        var product: Product? = null

        var bundle = arguments
        if (bundle != null) {
            product = bundle.getParcelable<Product>(PRODUCT)
        }

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ProductInfoViewModelFactory(product!!, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductInfoViewModel::class.java)

        binding.viewModel = viewModel

        binding.pageBtn.setOnClickListener {
            if(isOnline(requireContext())) {
                Navigation.findNavController(it).navigate(
                    R.id.action_productInfoFragment_to_productPageFragment
                )
            } else{
                Toast.makeText(context, OPEN_SOURCE_ERROR , Toast.LENGTH_LONG).show()
            }
        }

        binding.favoriteBtn.setOnClickListener{
            viewModel.setFavorite()
        }

        return binding.root
    }

}