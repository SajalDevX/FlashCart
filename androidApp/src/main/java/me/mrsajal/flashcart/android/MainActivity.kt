package me.mrsajal.flashcart.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.android.common.theming.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }.collect()

            }
        }
        setContent {
            AppTheme {
                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()
//                SideEffect {
//                    systemController.setSystemBarsColor(
//                        color = Color.Transparent,
//                        darkIcons = !isSystemInDarkMode
//                    )
//                }
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.background
                    ).statusBarsPadding()
                ) {
                    NavGraph(uiState = uiState)

                }
            }
        }
    }
}


