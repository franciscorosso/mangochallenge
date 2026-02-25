package com.example.mangochallenge.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangochallenge.core.domain.usecase.GetFavoriteCountUseCase
import com.example.mangochallenge.core.domain.usecase.GetUserProfileUseCase
import com.example.mangochallenge.core.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getFavoriteCountUseCase: GetFavoriteCountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ProfileUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<ProfileUiState>> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val user = getUserProfileUseCase()
                _uiState.value = UiState.Success(ProfileUiState(user = user))
                observeFavoriteCount()
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun observeFavoriteCount() {
        viewModelScope.launch {
            getFavoriteCountUseCase().collect { count ->
                _uiState.update { currentState ->
                    when (currentState) {
                        is UiState.Success -> UiState.Success(
                            currentState.data.copy(favoriteCount = count)
                        )
                        else -> currentState
                    }
                }
            }
        }
    }
}
