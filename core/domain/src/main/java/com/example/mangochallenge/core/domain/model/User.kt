package com.example.mangochallenge.core.domain.model

data class User(
    val id: Int,
    val email: String,
    val username: String,
    val name: UserName,
    val phone: String,
    val address: Address
)

data class UserName(
    val firstname: String,
    val lastname: String
)

data class Address(
    val city: String,
    val street: String,
    val number: Int,
    val zipcode: String
)
