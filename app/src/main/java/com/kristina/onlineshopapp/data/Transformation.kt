package com.kristina.onlineshopapp.data

import com.kristina.onlineshopapp.data.db.entity.ProductEntity
import com.kristina.onlineshopapp.data.network.model.ProductDto
import com.kristina.onlineshopapp.domain.model.Product

fun ProductEntity.toDomain(): Product {
    return Product(
        id, category, title, price, image, description, favourite, comment
    )
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id, category, title, price, image, description, favourite, comment
    )
}

fun Product.toEntity(base: ProductEntity): ProductEntity {
    return ProductEntity(
        id, category, title, price, image, description, base.favourite, base.comment
    )
}

fun ProductDto.toDomain() : Product {
    return Product(
        id, category, title, price, image, description
    )
}