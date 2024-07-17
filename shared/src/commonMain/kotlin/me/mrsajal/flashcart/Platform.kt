package me.mrsajal.flashcart

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform