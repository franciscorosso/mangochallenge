package com.example.mangochallenge.core.data.local.datasource

import com.example.mangochallenge.core.data.local.dao.FavoriteProductDao
import com.example.mangochallenge.core.data.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: FavoriteProductDao
) : LocalDataSource {

    override fun getAllFavorites(): Flow<List<FavoriteProductEntity>> {
        return dao.getAllFavorites()
    }

    override suspend fun getAllFavoriteIds(): List<Int> {
        return dao.getAllFavoriteIds()
    }

    override fun getFavoriteCount(): Flow<Int> {
        return dao.getFavoriteCount()
    }

    override suspend fun insertFavorite(product: FavoriteProductEntity) {
        dao.insertFavorite(product)
    }

    override suspend fun deleteFavorite(productId: Int) {
        dao.deleteFavorite(productId)
    }

    override suspend fun isFavorite(productId: Int): Boolean {
        return dao.isFavorite(productId)
    }
}
