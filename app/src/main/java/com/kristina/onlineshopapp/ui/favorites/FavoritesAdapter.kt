package com.kristina.onlineshopapp.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ToggleButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kristina.onlineshopapp.R
import com.kristina.onlineshopapp.databinding.FavoriteItemBinding
import com.kristina.onlineshopapp.domain.model.Product
import com.kristina.onlineshopapp.ui.product.list.ProductAdapter

class FavoritesAdapter(private val onRececlerViewItemClick: ProductAdapter.OnRececlerViewItemClick) :
    ListAdapter<Product, FavoritesAdapter.FavoriteProductViewHolder>(this) {

    companion object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product, newItem: Product
        ): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product, newItem: Product
        ): Boolean {
            //TODO Сравнить данные которые будут отображаться
            return ((oldItem.title == newItem.title)
                    && (oldItem.favourite == newItem.favourite)
                    && (oldItem.price == newItem.price))
        }
    }

    class FavoriteProductViewHolder(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.product = product
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteProductViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = FavoriteItemBinding.inflate(inflater, parent, false)
                return FavoriteProductViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductViewHolder {
        return FavoriteProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                onRececlerViewItemClick.sendProductToFragment(product)
            }
        }
        holder.itemView.findViewById<Button>(R.id.delete_from_favorites_btn).setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                onRececlerViewItemClick.setProductFavoriteStatus(product)
            }
        }
        holder.bind(product)
    }

}