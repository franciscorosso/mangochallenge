package com.example.mangochallenge.feature.favorites

import app.cash.turbine.test
import com.example.mangochallenge.core.domain.model.Product
import com.example.mangochallenge.core.domain.usecase.GetFavoritesUseCase
import com.example.mangochallenge.core.domain.usecase.ToggleFavoriteUseCase
import com.example.mangochallenge.core.testing.MainDispatcherRule
import com.example.mangochallenge.core.testing.testProduct
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoritesViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var getFavoritesUseCase: GetFavoritesUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    private val testFavorites = listOf(testProduct(isFavorite = true))

    @Before
    fun setup() {
        getFavoritesUseCase = mockk()
        toggleFavoriteUseCase = mockk(relaxed = true)
    }

    @Test
    fun `favorites flow emits favorites from use case`() = runTest {
        every { getFavoritesUseCase() } returns flowOf(testFavorites)

        val viewModel = FavoritesViewModel(getFavoritesUseCase, toggleFavoriteUseCase)

        viewModel.favorites.test {
            assertEquals(emptyList<Product>(), awaitItem())
            assertEquals(testFavorites, awaitItem())
        }
    }

    @Test
    fun `toggleFavorite calls use case`() = runTest {
        every { getFavoritesUseCase() } returns flowOf(testFavorites)

        val viewModel = FavoritesViewModel(getFavoritesUseCase, toggleFavoriteUseCase)
        dispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

        viewModel.toggleFavorite(testFavorites[0])
        dispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

        coVerify { toggleFavoriteUseCase(testFavorites[0]) }
    }
}
