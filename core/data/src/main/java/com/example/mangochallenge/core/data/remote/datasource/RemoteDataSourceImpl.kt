package com.example.mangochallenge.core.data.remote.datasource

import com.example.mangochallenge.core.data.remote.api.FakeStoreApi
import com.example.mangochallenge.core.data.remote.dto.ProductDto
import com.example.mangochallenge.core.data.remote.dto.UserDto
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: FakeStoreApi
) : RemoteDataSource {

    override suspend fun getProducts(): List<ProductDto> {
        return api.getProducts()
    }

    override suspend fun getUser(id: Int): UserDto {
        return api.getUser(id)
    }
}
