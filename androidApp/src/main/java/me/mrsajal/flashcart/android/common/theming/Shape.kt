package me.mrsajal.flashcart.android.common.theming

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import me.mrsajal.flashcart.android.common.theming.LargeSpacing
import me.mrsajal.flashcart.android.common.theming.MediumSpacing
import me.mrsajal.flashcart.android.common.theming.SmallSpacing

val Shapes = Shapes(
    small = RoundedCornerShape(SmallSpacing),
    medium = RoundedCornerShape(MediumSpacing),
    large = RoundedCornerShape(LargeSpacing)
)