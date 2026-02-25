package com.example.mangochallenge.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mangochallenge.core.data.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {

    @Query("SELECT * FROM favorite_products")
    fun getAllFavorites(): Flow<List<FavoriteProductEntity>>

    @Query("SELECT id FROM favorite_products")
    suspend fun getAllFavoriteIds(): List<Int>

    @Query("SELECT COUNT(*) FROM favorite_products")
    fun getFavoriteCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(product: FavoriteProductEntity)

    @Query("DELETE FROM favorite_products WHERE id = :productId")
    suspend fun deleteFavorite(productId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_products WHERE id = :productId)")
    suspend fun isFavorite(productId: Int): Boolean
}
