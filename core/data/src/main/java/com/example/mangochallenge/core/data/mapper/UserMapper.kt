package com.example.mangochallenge.core.data.mapper

import com.example.mangochallenge.core.data.remote.dto.UserDto
import com.example.mangochallenge.core.domain.model.Address
import com.example.mangochallenge.core.domain.model.User
import com.example.mangochallenge.core.domain.model.UserName

fun UserDto.toDomain(): User {
    return User(
        id = id,
        email = email,
        username = username,
        name = UserName(
            firstname = name.firstname,
            lastname = name.lastname
        ),
        phone = phone,
        address = Address(
            city = address.city,
            street = address.street,
            number = address.number,
            zipcode = address.zipcode
        )
    )
}
