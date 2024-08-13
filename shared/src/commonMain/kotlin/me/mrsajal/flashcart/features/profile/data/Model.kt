package me.mrsajal.flashcart.features.profile.data

import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val userId: String ,
    val name: String ,
    val email: String ,
    val password: String ,
    val imageUrl: String?=null,
    val userDetails: UserDetails,
)

@Serializable
data class UserDetails(
    val addresses: List<UserAddress> = emptyList(),
    val age: String? = null,
    val mobile: String,
    val gender: String,
    val userRole: String,
)

@Serializable
data class UserAddress(
    val fatherName: String,
    val motherName: String,
    val pin: String,
    val mobileNumber: String,
    val otherMobileNumber: String? = null,
    val city: String,
    val road: String,
    val state: String,
)
@Serializable
data class UpdateProfileRequest(
    val name: String,
    val imageUrl: String?=null,
    val mobile: String,
    val age: String,
    val gender: String,
)

@Serializable
data class AddUserAddressRequest(
    val fatherName: String,
    val motherName: String,
    val pin: String,
    val mobileNumber: String,
    val otherMobileNumber: String? = null,
    val city: String,
    val road: String,
    val state: String,
)