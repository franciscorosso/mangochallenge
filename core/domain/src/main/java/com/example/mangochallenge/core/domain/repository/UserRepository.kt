package com.example.mangochallenge.core.domain.repository

import com.example.mangochallenge.core.domain.model.User

interface UserRepository {
    suspend fun getUser(): User
}
