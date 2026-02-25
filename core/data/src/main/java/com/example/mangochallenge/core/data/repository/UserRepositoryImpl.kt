package com.example.mangochallenge.core.data.repository

import com.example.mangochallenge.core.data.mapper.toDomain
import com.example.mangochallenge.core.data.remote.datasource.RemoteDataSource
import com.example.mangochallenge.core.domain.model.User
import com.example.mangochallenge.core.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : UserRepository {

    override suspend fun getUser(): User {
        return remoteDataSource.getUser(1).toDomain()
    }
}
