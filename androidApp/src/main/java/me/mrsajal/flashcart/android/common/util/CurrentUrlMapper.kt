package me.mrsajal.flashcart.android.common.util

private const val CURRENT_BASE_URL = "http://192.168.177.198:8080/"

fun String.toCurrentUrl():String{
    return "$CURRENT_BASE_URL${this.substring(26)}"
}