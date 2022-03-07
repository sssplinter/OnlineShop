package com.kristina.onlineshopapp.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kristina.onlineshopapp.R
import com.kristina.onlineshopapp.databinding.FavoriteItemBinding
import com.kristina.onlineshopapp.domain.model.Product
import com.kristina.onlineshopapp.ui.product.list.ProductAdapter

class FavoritesAdapter(private val onClick: FavoriteProductRecyclerViewItemInterface) :
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

        with( holder.itemView) {
            setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    onClick.sendProductToFragment(product)
                }
            }
           findViewById<Button>(R.id.delete_from_favorites_btn)
                .setOnClickListener {
                    if (position != RecyclerView.NO_POSITION) {
                        onClick.setProductFavoriteStatus(product)
                    }
                }
           findViewById<Button>(R.id.add_comment_btn).setOnClickListener {
                val text = this.findViewById<EditText>(R.id.comment).text.toString()
                if (text != product.comment) {
                    onClick.addComment(product, text)
                }
            }
        }

        holder.bind(product)
    }

    interface FavoriteProductRecyclerViewItemInterface: ProductAdapter.ProductRececlerViewItemInterface{

        fun addComment(product: Product, comment: String)
    }

}