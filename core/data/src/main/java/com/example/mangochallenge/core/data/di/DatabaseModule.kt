package com.example.mangochallenge.core.data.di

import android.content.Context
import androidx.room.Room
import com.example.mangochallenge.core.data.local.MangoDatabase
import com.example.mangochallenge.core.data.local.dao.FavoriteProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MangoDatabase {
        return Room.databaseBuilder(
            context,
            MangoDatabase::class.java,
            "mango_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteProductDao(database: MangoDatabase): FavoriteProductDao {
        return database.favoriteProductDao()
    }
}
