package com.example.mangochallenge.core.data.remote.datasource

import com.example.mangochallenge.core.data.remote.dto.ProductDto
import com.example.mangochallenge.core.data.remote.dto.UserDto

interface RemoteDataSource {
    suspend fun getProducts(): List<ProductDto>
    suspend fun getUser(id: Int): UserDto
}
