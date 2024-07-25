package me.mrsajal.flashcart.features.product_review.data

import kotlinx.serialization.Serializable

@Serializable
data class RemoteReviewEntity(
val reviewId:String,
val userId:String,
val productId:String,
val username:String,
val caption:String,
val rating:Int=0,
val profilePic:String
)