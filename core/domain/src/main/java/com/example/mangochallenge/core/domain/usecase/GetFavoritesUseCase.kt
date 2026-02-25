package com.example.mangochallenge.core.domain.usecase

import com.example.mangochallenge.core.domain.model.Product
import com.example.mangochallenge.core.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return productRepository.getFavorites()
    }
}
