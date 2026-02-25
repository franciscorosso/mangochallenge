package com.example.mangochallenge.core.data.local.datasource

import com.example.mangochallenge.core.data.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAllFavorites(): Flow<List<FavoriteProductEntity>>
    suspend fun getAllFavoriteIds(): List<Int>
    fun getFavoriteCount(): Flow<Int>
    suspend fun insertFavorite(product: FavoriteProductEntity)
    suspend fun deleteFavorite(productId: Int)
    suspend fun isFavorite(productId: Int): Boolean
}
