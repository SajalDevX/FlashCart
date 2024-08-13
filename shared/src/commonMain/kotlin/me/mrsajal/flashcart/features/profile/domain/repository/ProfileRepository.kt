package me.mrsajal.flashcart.features.profile.domain.repository

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.profile.data.AddUserAddressRequest
import me.mrsajal.flashcart.features.profile.data.UpdateProfileRequest
import me.mrsajal.flashcart.features.profile.data.UserEntity

interface ProfileRepository {
    suspend fun getProfile(): Result<UserEntity>
    suspend fun updateProfile(
        updateProfile: UpdateProfileRequest,
        fileData: ByteArray,
        fileName: String
    ): Result<Boolean>

    suspend fun addAddress(
        updateAddress: AddUserAddressRequest
    ): Result<Boolean>
    suspend fun deleteAddress(
        index:Int
    ):Result<Boolean>
}