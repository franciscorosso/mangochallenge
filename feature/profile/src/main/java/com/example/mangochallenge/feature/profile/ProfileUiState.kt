package com.example.mangochallenge.feature.profile

import com.example.mangochallenge.core.domain.model.User

data class ProfileUiState(
    val user: User? = null,
    val favoriteCount: Int = 0
)
