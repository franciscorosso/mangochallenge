package com.example.mangochallenge.core.data.repository

import com.example.mangochallenge.core.data.local.datasource.LocalDataSource
import com.example.mangochallenge.core.data.local.entity.FavoriteProductEntity
import com.example.mangochallenge.core.data.remote.datasource.RemoteDataSource
import com.example.mangochallenge.core.data.remote.dto.ProductDto
import com.example.mangochallenge.core.data.remote.dto.RatingDto
import com.example.mangochallenge.core.testing.testProduct
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localDataSource: LocalDataSource
    private lateinit var repository: ProductRepositoryImpl

    private val testProductDto = ProductDto(
        id = 1,
        title = "Test Product",
        price = 9.99,
        description = "Test description",
        category = "test",
        image = "https://example.com/img.jpg",
        rating = RatingDto(rate = 4.5, count = 100)
    )

    private val testFavoriteEntity = FavoriteProductEntity(
        id = 1,
        title = "Test Product",
        price = 9.99,
        description = "Test description",
        category = "test",
        image = "https://example.com/img.jpg",
        ratingRate = 4.5,
        ratingCount = 100
    )

    @Before
    fun setup() {
        remoteDataSource = mockk()
        localDataSource = mockk()
        repository = ProductRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getProducts merges remote products with local favorites`() = runTest {
        coEvery { remoteDataSource.getProducts() } returns listOf(testProductDto)
        coEvery { localDataSource.getAllFavoriteIds() } returns listOf(1)

        val products = repository.getProducts()

        assertEquals(1, products.size)
        assertTrue(products[0].isFavorite)
        assertEquals("Test Product", products[0].title)
    }

    @Test
    fun `getProducts marks non-favorited products correctly`() = runTest {
        coEvery { remoteDataSource.getProducts() } returns listOf(testProductDto)
        coEvery { localDataSource.getAllFavoriteIds() } returns emptyList()

        val products = repository.getProducts()

        assertFalse(products[0].isFavorite)
    }

    @Test
    fun `getFavorites returns mapped entities from local data source`() = runTest {
        every { localDataSource.getAllFavorites() } returns flowOf(listOf(testFavoriteEntity))

        val favorites = repository.getFavorites().first()

        assertEquals(1, favorites.size)
        assertTrue(favorites[0].isFavorite)
        assertEquals("Test Product", favorites[0].title)
    }

    @Test
    fun `toggleFavorite removes favorite when already favorited`() = runTest {
        val product = testProduct(isFavorite = true)
        coEvery { localDataSource.isFavorite(1) } returns true
        coEvery { localDataSource.deleteFavorite(1) } returns Unit

        repository.toggleFavorite(product)

        coVerify { localDataSource.deleteFavorite(1) }
    }

    @Test
    fun `toggleFavorite adds favorite when not favorited`() = runTest {
        val product = testProduct(id = 2, isFavorite = false)
        coEvery { localDataSource.isFavorite(2) } returns false
        coEvery { localDataSource.insertFavorite(any()) } returns Unit

        repository.toggleFavorite(product)

        coVerify { localDataSource.insertFavorite(any()) }
    }

    @Test
    fun `getFavoriteCount returns count from local data source`() = runTest {
        every { localDataSource.getFavoriteCount() } returns flowOf(5)

        val count = repository.getFavoriteCount().first()

        assertEquals(5, count)
    }
}
