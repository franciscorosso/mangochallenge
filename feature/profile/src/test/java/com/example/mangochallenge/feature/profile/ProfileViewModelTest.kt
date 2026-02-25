package com.example.mangochallenge.feature.profile

import app.cash.turbine.test
import com.example.mangochallenge.core.domain.usecase.GetFavoriteCountUseCase
import com.example.mangochallenge.core.domain.usecase.GetUserProfileUseCase
import com.example.mangochallenge.core.testing.MainDispatcherRule
import com.example.mangochallenge.core.testing.testUser
import com.example.mangochallenge.core.ui.UiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var getUserProfileUseCase: GetUserProfileUseCase
    private lateinit var getFavoriteCountUseCase: GetFavoriteCountUseCase

    private val user = testUser()

    @Before
    fun setup() {
        getUserProfileUseCase = mockk()
        getFavoriteCountUseCase = mockk()
    }

    @Test
    fun `initial state loads profile successfully`() = runTest {
        coEvery { getUserProfileUseCase() } returns user
        every { getFavoriteCountUseCase() } returns flowOf(3)

        val viewModel = ProfileViewModel(getUserProfileUseCase, getFavoriteCountUseCase)

        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.Loading)
            val success = awaitItem()
            assertTrue(success is UiState.Success)
            val data = (success as UiState.Success).data
            assertEquals(user, data.user)
            // Favorite count may arrive in same or next emission
            val finalState = if (data.favoriteCount == 3) data else (awaitItem() as UiState.Success).data
            assertEquals(3, finalState.favoriteCount)
        }
    }

    @Test
    fun `load profile returns error on exception`() = runTest {
        coEvery { getUserProfileUseCase() } throws RuntimeException("Network error")
        every { getFavoriteCountUseCase() } returns flowOf(0)

        val viewModel = ProfileViewModel(getUserProfileUseCase, getFavoriteCountUseCase)

        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.Loading)
            val error = awaitItem()
            assertTrue(error is UiState.Error)
            assertEquals("Network error", (error as UiState.Error).message)
        }
    }
}
