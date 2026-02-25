package com.example.mangochallenge.core.testing

import com.example.mangochallenge.core.domain.model.Address
import com.example.mangochallenge.core.domain.model.Product
import com.example.mangochallenge.core.domain.model.Rating
import com.example.mangochallenge.core.domain.model.User
import com.example.mangochallenge.core.domain.model.UserName

fun testProduct(
    id: Int = 1,
    title: String = "Test Product",
    price: Double = 9.99,
    description: String = "Test description",
    category: String = "test",
    image: String = "https://example.com/img.jpg",
    rate: Double = 4.5,
    count: Int = 100,
    isFavorite: Boolean = false
) = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = Rating(rate = rate, count = count),
    isFavorite = isFavorite
)

fun testUser(
    id: Int = 1,
    email: String = "john@gmail.com",
    username: String = "johnd",
    firstname: String = "John",
    lastname: String = "Doe",
    phone: String = "1-570-236-7033",
    city: String = "kilcoole",
    street: String = "new road",
    number: Int = 7682,
    zipcode: String = "12926-3874"
) = User(
    id = id,
    email = email,
    username = username,
    name = UserName(firstname = firstname, lastname = lastname),
    phone = phone,
    address = Address(city = city, street = street, number = number, zipcode = zipcode)
)
