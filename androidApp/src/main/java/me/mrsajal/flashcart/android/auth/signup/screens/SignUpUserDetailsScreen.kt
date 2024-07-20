package me.mrsajal.flashcart.android.auth.signup.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.auth.common.CustomButton
import me.mrsajal.flashcart.android.auth.signup.SignUpUiState
import me.mrsajal.flashcart.android.common.util.components.CustomTextField
import me.mrsajal.flashcart.android.common.util.routes.Routes

@Composable
fun UserDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    buttonText: Int,
    uiState: SignUpUiState,
    onNameChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onRoleChange: (String) -> Unit,
    onNavigateToPasswordScreen: () -> Unit
) {
    var expandedGender by remember { mutableStateOf(false) }
    val genderOptions = listOf("Male", "Female", "Other")

    var expandedRole by remember { mutableStateOf(false) }
    val roleOptions = listOf("User", "Admin", "Seller","Super admin")

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.fillMaxWidth(),
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.SignUpMobile.route) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            modifier = modifier.size(24.dp),
                            contentDescription = null,
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .width(328.dp)
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.userdetails),
                    contentDescription = null,
                    modifier = Modifier
                        .height(30.dp),
                    contentScale = ContentScale.Crop,
                    colorFilter = if(isSystemInDarkTheme()){ ColorFilter.tint(Color.Green) }else{ColorFilter.tint(Color(0xFF008C0C))}
                )
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.user_details_heading),
                    modifier = modifier.width(192.dp),
                    fontWeight = FontWeight(700),
                    fontSize = 20.sp,
                    lineHeight = 24.sp
                )
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.user_detail_subheading),
                    modifier = modifier
                        .height(40.dp)
                        .width(328.dp),
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(400)
                )
                Spacer(modifier = modifier.height(24.dp))
                CustomTextField(
                    value = uiState.username,
                    onValueChange = onNameChange,
                    hint = R.string.username_hint,
                    isSingleLine = true,
                    isValid = true
                )
                Spacer(modifier = modifier.height(10.dp))
                CustomTextField(
                    value = uiState.age ?: "",
                    onValueChange = onAgeChange,
                    hint = R.string.age_hint,
                    isSingleLine = true,
                    keyboardType = KeyboardType.Number,
                    isValid = uiState.errorMessage?.contains("age", ignoreCase = true) == null,
                )
                Spacer(modifier = modifier.height(10.dp))

                // Gender Button
                var selectedGender by remember { mutableStateOf(uiState.gender.ifEmpty { "Choose gender" }) }
                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Column {
                        Button(
                            onClick = { expandedGender = true },
                            modifier = modifier.width(146.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF008C0C)),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(text = selectedGender)
                        }
                        DropdownMenu(
                            modifier = modifier.width(146.dp),
                            expanded = expandedGender,
                            onDismissRequest = { expandedGender = false }
                        ) {
                            genderOptions.forEach { option ->
                                DropdownMenuItem(onClick = {
                                    onGenderChange(option)
                                    selectedGender = option
                                    expandedGender = false
                                }) {
                                    Text(text = option)
                                }
                            }
                        }
                    }
                    Spacer(modifier = modifier.height(24.dp))

                    // Role Button
                    var selectedRole by remember { mutableStateOf(uiState.userRole.ifEmpty { "Choose Role" }) }
                    Column {
                        Button(
                            onClick = { expandedRole = true },
                            modifier = modifier.width(146.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF008C0C))
                        ) {
                            Text(text = selectedRole)
                        }
                        DropdownMenu(
                            modifier = modifier.width(150.dp),
                            expanded = expandedRole,
                            onDismissRequest = { expandedRole = false }
                        ) {
                            roleOptions.forEach { option ->
                                DropdownMenuItem(onClick = {
                                    onRoleChange(option)
                                    selectedRole = option
                                    expandedRole = false
                                }) {
                                    Text(text = option)
                                }
                            }
                        }
                    }
                }
                if (uiState.errorMessage != null) {
                    Spacer(modifier = modifier.height(8.dp))
                    Text(
                        text = uiState.errorMessage,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            CustomButton(
                text = stringResource(buttonText),
                onClick = onNavigateToPasswordScreen
            )
            println("The user Details are ${uiState.username} ${uiState.age} ${uiState.gender} ${uiState.userRole}, email is ${uiState.email} phone number is ${uiState.phoneNumber}")
        }
    }
}
