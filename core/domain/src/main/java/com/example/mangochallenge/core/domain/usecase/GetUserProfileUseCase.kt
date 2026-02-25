package com.example.mangochallenge.core.domain.usecase

import com.example.mangochallenge.core.domain.model.User
import com.example.mangochallenge.core.domain.repository.UserRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User {
        return userRepository.getUser()
    }
}
