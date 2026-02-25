package com.example.mangochallenge.feature.products

import app.cash.turbine.test
import com.example.mangochallenge.core.domain.usecase.GetProductsUseCase
import com.example.mangochallenge.core.domain.usecase.ToggleFavoriteUseCase
import com.example.mangochallenge.core.testing.MainDispatcherRule
import com.example.mangochallenge.core.testing.testProduct
import com.example.mangochallenge.core.ui.UiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    private val testProducts = listOf(testProduct())

    @Before
    fun setup() {
        getProductsUseCase = mockk()
        toggleFavoriteUseCase = mockk(relaxed = true)
    }

    @Test
    fun `initial state loads products successfully`() = runTest {
        coEvery { getProductsUseCase() } returns testProducts

        val viewModel = ProductsViewModel(getProductsUseCase, toggleFavoriteUseCase)

        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.Loading)
            val success = awaitItem()
            assertTrue(success is UiState.Success)
            assertEquals(testProducts, (success as UiState.Success).data)
        }
    }

    @Test
    fun `load products returns error on exception`() = runTest {
        coEvery { getProductsUseCase() } throws RuntimeException("Network error")

        val viewModel = ProductsViewModel(getProductsUseCase, toggleFavoriteUseCase)

        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.Loading)
            val error = awaitItem()
            assertTrue(error is UiState.Error)
            assertEquals("Network error", (error as UiState.Error).message)
        }
    }

    @Test
    fun `toggleFavorite calls use case and reloads`() = runTest {
        coEvery { getProductsUseCase() } returns testProducts

        val viewModel = ProductsViewModel(getProductsUseCase, toggleFavoriteUseCase)
        dispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

        viewModel.toggleFavorite(testProducts[0])
        dispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

        coVerify { toggleFavoriteUseCase(testProducts[0]) }
        coVerify(atLeast = 2) { getProductsUseCase() }
    }
}
