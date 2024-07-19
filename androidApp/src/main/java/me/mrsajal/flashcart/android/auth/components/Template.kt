package me.mrsajal.flashcart.android.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.mrsajal.flashcart.android.common.theming.ButtonHeight
import me.mrsajal.flashcart.android.common.theming.ExtraLargeSpacing
import me.mrsajal.flashcart.android.common.theming.LargeSpacing
import me.mrsajal.flashcart.android.common.theming.MediumSpacing
import me.mrsajal.flashcart.android.common.util.components.CustomTextField

@Composable
fun AuthScreenTemplate(
    modifier: Modifier = Modifier,
    heading: Int,
    logo: Int,
    subHeading: Int,
    textFields: List<Pair<Int, String>>,
    onValueChange: (Int, String) -> Unit,
    onButtonClick: () -> Unit,
    buttonText: Int,
    onNavigateToLogin: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colors.background
                    } else {
                        MaterialTheme.colors.surface
                    }
                )
                .padding(
                    top = ExtraLargeSpacing + LargeSpacing,
                    start = LargeSpacing + MediumSpacing,
                    end = LargeSpacing + MediumSpacing,
                    bottom = LargeSpacing
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LargeSpacing)
        ) {

            textFields.forEachIndexed { index, (label, value) ->
                var fieldValue by remember { mutableStateOf(value) }
                CustomTextField(
                    value = fieldValue,
                    onValueChange = { newValue ->
                        fieldValue = newValue
                        onValueChange(index, newValue)
                    },
                    hint = label
                )
            }

            Button(
                onClick = onButtonClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = stringResource(id = buttonText))
            }


        }
    }
}
