package me.mrsajal.flashcart.android.auth.common


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.mrsajal.flashcart.android.common.theming.AppTheme

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {

        Button(
            modifier = modifier
                .width(328.dp)
                .height(54.dp),
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(Color(0xFF008C0C)),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.elevation(0.dp)
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.W600,
                color = Color.White
            )
        }

}

@Preview
@Composable
fun CustomButtonPreview() {
    AppTheme {
        CustomButton(text = "Continue") {
            // Click action here
        }
    }
}
