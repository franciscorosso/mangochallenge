package com.example.mangochallenge.core.domain.usecase

import com.example.mangochallenge.core.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteCountUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): Flow<Int> {
        return productRepository.getFavoriteCount()
    }
}
