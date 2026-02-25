package com.example.mangochallenge.core.data.mapper

import com.example.mangochallenge.core.data.local.entity.FavoriteProductEntity
import com.example.mangochallenge.core.data.remote.dto.ProductDto
import com.example.mangochallenge.core.domain.model.Product
import com.example.mangochallenge.core.domain.model.Rating

fun ProductDto.toDomain(isFavorite: Boolean = false): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = Rating(rate = rating.rate, count = rating.count),
        isFavorite = isFavorite
    )
}

fun Product.toEntity(): FavoriteProductEntity {
    return FavoriteProductEntity(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        ratingRate = rating.rate,
        ratingCount = rating.count
    )
}

fun FavoriteProductEntity.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = Rating(rate = ratingRate, count = ratingCount),
        isFavorite = true
    )
}
