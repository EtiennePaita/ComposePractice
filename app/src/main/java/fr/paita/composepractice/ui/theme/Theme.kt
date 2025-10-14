package fr.paita.composepractice.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


// 1. Use theme colors for basics
private val DefaultColorScheme = darkColorScheme(
    // surfaces
    background = Black, // app background
    surface = DarkGrey, // Dialog, Surface, TopAppBar
    surfaceVariant = Black, //
    primaryContainer = DarkGrey, // Floating buttons
    secondaryContainer = Black, //
    tertiaryContainer = Black, //
    errorContainer = Black, // Alerts
    inverseSurface = Black, //

    // texts
    onBackground = VeryLightGrey, // onBackground Text color (only for higher hierarchy Text un-encapsulated inside complex Composable like Button or Card)
    onSurface = VeryLightGrey, // text on Card, Top & Bottom App Bar
    onSurfaceVariant = VeryLightGrey,
    onPrimary = VeryLightGrey, // Button's text
    onSecondary = VeryLightGrey,
    onTertiary = VeryLightGrey,
    onPrimaryContainer = VeryLightGrey, // text on Surface, FloatingActionButton content
    onSecondaryContainer = VeryLightGrey,
    onTertiaryContainer = VeryLightGrey,
    onError = RedError,
    onErrorContainer = VeryLightGrey,
    inverseOnSurface = VeryLightGrey,
    inversePrimary = VeryLightGrey,

    /// other
    primary = PrimaryAppColor, // Buttons, Top & Bottom bar content/text, principal actions
    error = RedError, // Borders if invalid fields, Cancel button
    //outline = PrimaryAppColor, // Dividers, TextField borders
    //outlineVariant = PrimaryAppColor, // variant
    //secondary = DarkGrey,
    //tertiary = DarkGrey,

)

// 2. LocalAppColors with my custom colors and names for specifics designs
data class AppColors(
    val cardColor: Color
)

private val DefaultAppColors = AppColors(
    cardColor = LightGrey,
)

val LocalAppColors = staticCompositionLocalOf {
    DefaultAppColors
}

@Composable
fun ComposePracticeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        /*dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*/
        darkTheme -> DefaultColorScheme
        else -> DefaultColorScheme
    }

    val appColors = when {
        darkTheme -> DefaultAppColors
        else -> DefaultAppColors
    }

    // Set status bar colors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // set status bar background color
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                    val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
                    view.setBackgroundColor(colorScheme.primary.toArgb())

                    // Adjust padding to avoid overlap
                    view.setPadding(0, statusBarInsets.top, 0, 0)
                    insets
                }
            } else {
                window.statusBarColor = colorScheme.primary.toArgb()
            } */
            // set status bar foreground appearance
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    //Do not use CompositionLocalProvider if LocalAppColors values won't changed
    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}