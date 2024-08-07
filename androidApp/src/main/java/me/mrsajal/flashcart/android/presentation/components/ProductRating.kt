import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProductRating(
    modifier: Modifier = Modifier,
    rating: Int,
    maxRating: Int = 5,
    size: Dp = 20.dp
) {
    Row(
        modifier = modifier
            .width(120.dp)
            .height(25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxRating) {
            val starIcon = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star
            val starColor = if (i <= rating) Color.Green else Color.Gray.copy(alpha = 0.3f)
            Icon(
                imageVector = starIcon,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier
                    .size(size)
                    .padding(2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductRatingPreview() {
    ProductRating(rating = 4)
}
