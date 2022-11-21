package uk.co.jofaircloth.memring.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    secondary = secondary,
    tertiary = tertiary,
    error = error,
    background = background,
    outline = outline,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onTertiary = onTertiary,
    onError = onError,
    onBackground = onBackground,
    primaryContainer = primaryContainer,
    secondaryContainer = secondaryContainer,
    tertiaryContainer = tertiaryContainer,
    errorContainer = errorContainer,
    surface = surface,
    surfaceVariant = surfaceVariant,
    onPrimaryContainer = onPrimaryContainer,
    onSecondaryContainer = onSecondaryContainer,
    onTertiaryContainer = onTertiaryContainer,
    onErrorContainer = onErrorContainer,
    onSurface = onSurface,
    onSurfaceVariant = onSurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    primary = primary,
    secondary = secondary,
    tertiary = tertiary,
    error = error,
    background = background,
    outline = outline,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onTertiary = onTertiary,
    onError = onError,
    onBackground = onBackground,
    primaryContainer = primaryContainer,
    secondaryContainer = secondaryContainer,
    tertiaryContainer = tertiaryContainer,
    errorContainer = errorContainer,
    surface = surface,
    surfaceVariant = surfaceVariant,
    onPrimaryContainer = onPrimaryContainer,
    onSecondaryContainer = onSecondaryContainer,
    onTertiaryContainer = onTertiaryContainer,
    onErrorContainer = onErrorContainer,
    onSurface = onSurface,
    onSurfaceVariant = onSurfaceVariant
)

@Composable
fun MemringTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
// scaffold code.  above is Reply sample code
//        SideEffect {
//            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
//            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
//        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        typography = Typography,
        content = content
    )
}