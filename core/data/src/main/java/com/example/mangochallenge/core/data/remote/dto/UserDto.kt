package com.example.mangochallenge.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: UserNameDto,
    @SerializedName("phone") val phone: String,
    @SerializedName("address") val address: AddressDto
)

data class UserNameDto(
    @SerializedName("firstname") val firstname: String,
    @SerializedName("lastname") val lastname: String
)

data class AddressDto(
    @SerializedName("city") val city: String,
    @SerializedName("street") val street: String,
    @SerializedName("number") val number: Int,
    @SerializedName("zipcode") val zipcode: String,
    @SerializedName("geolocation") val geolocation: GeolocationDto
)

data class GeolocationDto(
    @SerializedName("lat") val lat: String,
    @SerializedName("long") val long: String
)
