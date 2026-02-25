package com.example.mangochallenge.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mangochallenge.core.data.local.dao.FavoriteProductDao
import com.example.mangochallenge.core.data.local.entity.FavoriteProductEntity

@Database(
    entities = [FavoriteProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MangoDatabase : RoomDatabase() {
    abstract fun favoriteProductDao(): FavoriteProductDao
}
