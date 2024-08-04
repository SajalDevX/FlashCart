package me.mrsajal.flashcart.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalDivider(
    color: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
    thickness: Dp = 1.dp,
    height: Dp = 100.dp
) {
    Box(
        modifier = Modifier
            .width(thickness)
            .height(height)
            .background(color)
    )
}