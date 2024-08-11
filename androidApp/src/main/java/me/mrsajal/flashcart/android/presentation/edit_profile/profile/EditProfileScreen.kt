package me.mrsajal.flashcart.android.presentation.edit_profile.profile

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.presentation.profile.components.ProfileTextField
import java.io.File
import java.io.FileOutputStream

@Composable
fun EditProfileScreen(
    uiState: EditProfileUiState,
    updateFile: (String, ByteArray) -> Unit,
    nameTextFieldValue: TextFieldValue,
    mobileTextFieldValue: TextFieldValue,
    genderTextFieldValue: TextFieldValue,
    ageTextFieldValue: TextFieldValue,
    onUiAction: (ProfileUiAction) -> Unit,
    onNameTextFieldValueChange: (TextFieldValue) -> Unit,
    onMobileTextFieldValueChange: (TextFieldValue) -> Unit,
    onAgeTextFieldValueChange: (TextFieldValue) -> Unit,
    onGenderTextFieldValueChange: (TextFieldValue) -> Unit
) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    selectedImageUri = uri
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val tempFile = File(context.cacheDir, "profile_image.jpg")
                    FileOutputStream(tempFile).use { outputStream ->
                        bitmap.compress(
                            android.graphics.Bitmap.CompressFormat.JPEG,
                            100,
                            outputStream
                        )
                    }
                    val fileData = tempFile.readBytes()
                    updateFile(tempFile.name, fileData)
                }
            }
        }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = selectedImageUri ?: uiState.profileImage,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                    )
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile Image",
                        tint = Color.White,
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .clickable {
                                val intent = Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                                launcher.launch(intent)
                            }
                            .padding(4.dp)
                    )
                }
            }


            ProfileTextField(
                value = nameTextFieldValue.text,
                onValueChange = { onNameTextFieldValueChange(TextFieldValue(it)) },
                label = R.string.enter_name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            ProfileTextField(
                value = ageTextFieldValue.text,
                onValueChange = { onAgeTextFieldValueChange(TextFieldValue(it)) },
                label = R.string.enter_age,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            ProfileTextField(
                value = mobileTextFieldValue.text,
                onValueChange = { onMobileTextFieldValueChange(TextFieldValue(it)) },
                label = R.string.enter_mobile,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            ProfileTextField(
                value = genderTextFieldValue.text,
                onValueChange = { onGenderTextFieldValueChange(TextFieldValue(it)) },
                label = R.string.enter_gender,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )



            Button(
                onClick = { onUiAction(ProfileUiAction.SaveProfileAction) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (uiState.saveLoading) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier
                                .size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Saving...")
                    }
                } else {
                    Text(text = "Save Profile")
                }
            }

            LaunchedEffect(key1 = uiState.successMessage, key2 = uiState.errorMessage) {
                if (uiState.successMessage != null) {
                    Toast.makeText(context, uiState.successMessage, Toast.LENGTH_SHORT).show()
                }else if(uiState.errorMessage!=null){
                    Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}

