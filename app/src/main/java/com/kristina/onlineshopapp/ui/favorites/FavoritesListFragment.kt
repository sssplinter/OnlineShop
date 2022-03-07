package com.kristina.onlineshopapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kristina.onlineshopapp.R
import com.kristina.onlineshopapp.databinding.FragmentFavoritesListBinding
import com.kristina.onlineshopapp.domain.model.Product
import com.kristina.onlineshopapp.ui.product.list.ProductAdapter

class FavoritesListFragment : Fragment(), FavoritesAdapter.FavoriteProductRecyclerViewItemInterface {

    lateinit var binding: FragmentFavoritesListBinding
    lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_favorites_list, container, false
        )

        val viewModelFactory = FavoritesViewModelFactory(binding.root.context.applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)

        val adapter = FavoritesAdapter(this)
        binding.favoritesList.adapter = adapter

        viewModel?.favorites?.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun addComment(product: Product, comment: String) {
        viewModel?.addComment(product, comment)
    }

    override fun sendProductToFragment(product: Product) {
        findNavController().navigate(
            R.id.action_favoritesListFragment_to_productInfoFragment, bundleOf("product" to product)
        )
    }

    override fun setProductFavoriteStatus(product: Product) {
        viewModel?.setFavorite(product)
    }

}