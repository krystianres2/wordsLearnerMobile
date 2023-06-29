package com.example.wordslearner.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
//    background = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.White
)

@Composable
fun WordsLearnerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val view = LocalView.current
    if (!view.isInEditMode){
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = LightColorPalette.primaryVariant.toArgb()
            if (darkTheme){
                window.statusBarColor = DarkColorPalette.primary.toArgb()
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =darkTheme
        }
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}