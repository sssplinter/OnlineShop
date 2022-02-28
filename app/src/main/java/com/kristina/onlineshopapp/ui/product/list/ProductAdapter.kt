package com.kristina.onlineshopapp.ui.product.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kristina.onlineshopapp.databinding.ProductItemBinding
import com.kristina.onlineshopapp.domain.model.Product

class ProductAdapter (private val onClickListener: OnClickListener): ListAdapter<Product, ProductViewHolder>(this) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
//        holder.itemView.setOnClickListener{
//            val position = holder.bindingAdapterPosition
//            if(position != RecyclerView.NO_POSITION){
//                onClickListener.onClick(product)
//            }
//
//        }
        holder.bind(product)
    }

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
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (product:Product) -> Unit){
        fun onClick(product: Product) = clickListener(product)
    }
}

class ProductViewHolder(private val binding: ProductItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
    }

    companion object {
        fun from(parent: ViewGroup): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProductItemBinding.inflate(inflater, parent, false)
            return ProductViewHolder(
                binding
            )
        }
    }
}


