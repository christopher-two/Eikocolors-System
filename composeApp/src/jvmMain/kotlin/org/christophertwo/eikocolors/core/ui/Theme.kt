package org.christophertwo.eikocolors.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec

@Composable
fun EikoColorsSystemTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    seedColor: Color = Color(0xFF68FFD4),
    content: @Composable () -> Unit
) {
    DynamicMaterialTheme(
        seedColor = seedColor,
        isDark = isDarkTheme,
        specVersion = ColorSpec.SpecVersion.SPEC_2025,
        style = PaletteStyle.Monochrome,
        content = {
            Surface(
                color = colorScheme.background,
                contentColor = colorScheme.onBackground,
                modifier = Modifier.fillMaxSize(),
                content = content
            )
        }
    )
}