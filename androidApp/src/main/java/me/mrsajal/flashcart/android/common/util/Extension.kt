package me.mrsajal.flashcart.android.common.util

import java.text.NumberFormat
import java.util.Locale

fun Int.formatWithCommas(): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    return formatter.format(this)
}