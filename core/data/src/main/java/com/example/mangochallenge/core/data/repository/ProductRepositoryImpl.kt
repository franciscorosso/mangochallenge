package com.example.mangochallenge.core.data.repository

import com.example.mangochallenge.core.data.local.datasource.LocalDataSource
import com.example.mangochallenge.core.data.mapper.toDomain
import com.example.mangochallenge.core.data.mapper.toEntity
import com.example.mangochallenge.core.data.remote.datasource.RemoteDataSource
import com.example.mangochallenge.core.domain.model.Product
import com.example.mangochallenge.core.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        val favoriteIds = localDataSource.getAllFavoriteIds().toSet()
        return remoteDataSource.getProducts().map { dto ->
            dto.toDomain(isFavorite = dto.id in favoriteIds)
        }
    }

    override fun getFavorites(): Flow<List<Product>> {
        return localDataSource.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(product: Product) {
        if (localDataSource.isFavorite(product.id)) {
            localDataSource.deleteFavorite(product.id)
        } else {
            localDataSource.insertFavorite(product.toEntity())
        }
    }

    override fun getFavoriteCount(): Flow<Int> {
        return localDataSource.getFavoriteCount()
    }
}
