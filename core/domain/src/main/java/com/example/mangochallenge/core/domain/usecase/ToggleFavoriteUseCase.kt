package com.example.mangochallenge.core.domain.usecase

import com.example.mangochallenge.core.domain.model.Product
import com.example.mangochallenge.core.domain.repository.ProductRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(product: Product) {
        productRepository.toggleFavorite(product)
    }
}
