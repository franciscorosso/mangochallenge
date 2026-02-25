package com.example.mangochallenge.core.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mangochallenge.core.data.local.MangoDatabase
import com.example.mangochallenge.core.data.local.dao.FavoriteProductDao
import com.example.mangochallenge.core.data.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteProductDaoTest {

    private lateinit var database: MangoDatabase
    private lateinit var dao: FavoriteProductDao

    private val testEntity = FavoriteProductEntity(
        id = 1,
        title = "Test Product",
        price = 9.99,
        description = "Description",
        category = "test",
        image = "https://example.com/img.jpg",
        ratingRate = 4.5,
        ratingCount = 100
    )

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MangoDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.favoriteProductDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetFavorite() = runTest {
        dao.insertFavorite(testEntity)
        val favorites = dao.getAllFavorites().first()
        assertEquals(1, favorites.size)
        assertEquals(testEntity, favorites[0])
    }

    @Test
    fun deleteFavorite() = runTest {
        dao.insertFavorite(testEntity)
        dao.deleteFavorite(testEntity.id)
        val favorites = dao.getAllFavorites().first()
        assertTrue(favorites.isEmpty())
    }

    @Test
    fun isFavorite() = runTest {
        assertFalse(dao.isFavorite(testEntity.id))
        dao.insertFavorite(testEntity)
        assertTrue(dao.isFavorite(testEntity.id))
    }

    @Test
    fun getFavoriteCount() = runTest {
        assertEquals(0, dao.getFavoriteCount().first())
        dao.insertFavorite(testEntity)
        assertEquals(1, dao.getFavoriteCount().first())
    }

    @Test
    fun getAllFavoriteIds() = runTest {
        dao.insertFavorite(testEntity)
        val ids = dao.getAllFavoriteIds()
        assertEquals(listOf(1), ids)
    }
}
