package me.mrsajal.flashcart.android.presentation.users.customer.place_order.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.mrsajal.flashcart.android.presentation.users.customer.place_order.progressbarWidth

@Composable
fun LinearProgressBar(steps: List<String>, currentStep: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        steps.forEachIndexed { index, step ->
            if (index > 0) {
                ConnectingLine(
                    isCompleted = index <= currentStep,
                )
            }
            ProgressStep(
                number = index + 1,
                label = step,
                isCompleted = index < currentStep,
                isActive = index == currentStep
            )
        }
    }
}

@Composable
fun ProgressStep(number: Int, label: String, isCompleted: Boolean, isActive: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = when {
                        isCompleted -> Color.Blue
                        isActive -> Color.Blue.copy(alpha = 0.8f)
                        else -> Color.LightGray
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Completed",
                    tint = Color.White
                )
            } else {
                Text(
                    text = number.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = if (isActive) Color.Black else Color.Gray,
            fontSize = if(isActive) 18.sp else 14.sp,
            fontWeight = if (isActive) FontWeight.ExtraBold else FontWeight.Normal
        )
    }
}

@Composable
fun ConnectingLine(isCompleted: Boolean) {
    Box(
        modifier = Modifier
            .width(progressbarWidth / 5)
            .height(2.dp)
            .background(
                color = if (isCompleted) Color.Blue else Color.LightGray
            ),
    )
}

@Preview
@Composable
fun PreviewLinearProgressBar() {
    val steps = listOf("Address", "Order Summary", "Payment")
    LinearProgressBar(steps = steps, currentStep = 1)
}