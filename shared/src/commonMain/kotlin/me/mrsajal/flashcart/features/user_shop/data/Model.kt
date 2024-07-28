package me.mrsajal.flashcart.features.user_shop.data

import kotlinx.serialization.Serializable


@Serializable
data class RemoteShopEntity(
    val shopId:String,
    val userId:String,
    val shopCategoryId: String,
    val shopCategoryName:String,
    val shopName:String,
)