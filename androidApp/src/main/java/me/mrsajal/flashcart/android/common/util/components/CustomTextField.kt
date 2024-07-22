package me.mrsajal.flashcart.android.common.util.components

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.common.theming.AppTheme
import me.mrsajal.flashcart.android.common.theming.Gray

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordTextField: Boolean = false,
    isSingleLine: Boolean = true,
    @StringRes hint: Int,
    isValid: Boolean,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    val themeColor = if (isSystemInDarkTheme()) {
        MaterialTheme.colors.surface
    } else {
        Gray
    }

    val borderColor = if (isValid) {
        Color(0xFF67C4A7)
    } else {
        Red
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, borderColor, shape = RoundedCornerShape(12.dp))
            .padding(1.dp),
        textStyle = MaterialTheme.typography.body2,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),

        singleLine = isSingleLine,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = themeColor,
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor
        ),
        trailingIcon = if (isPasswordTextField) {
            {
                PasswordEyeIcon(isPasswordVisible = isPasswordVisible) {
                    isPasswordVisible = !isPasswordVisible
                }
            }
        } else {
            null
        },
        visualTransformation = if (isPasswordTextField) {
            if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        } else {
            VisualTransformation.None
        },
        isError = !isValid,

        placeholder = {
            Text(text = stringResource(id = hint), style = MaterialTheme.typography.body2)
        },
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun PasswordEyeIcon(
    isPasswordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit
) {
    val image = if (isPasswordVisible) {
        painterResource(id = R.drawable.show_eye_icon_filled)
    } else {
        painterResource(id = R.drawable.hide_eye_icon_filled)
    }

    IconButton(onClick = onPasswordVisibilityToggle) {
        Icon(painter = image, contentDescription = null)
    }
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    AppTheme {
        CustomTextField(
            value = "",
            onValueChange = {},
            hint = R.string.loading_error_message,
            isValid = true
        )
    }
}
