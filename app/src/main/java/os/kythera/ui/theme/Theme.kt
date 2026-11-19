package os.kythera.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object KytheraColors {
    val Midnight = Color(0xFF070B1A)
    val DeepViolet = Color(0xFF1B1146)
    val Cyan = Color(0xFF5EEAD4)
    val Violet = Color(0xFFA78BFA)
    val GlassFill = Color(0x33FFFFFF)
    val GlassBorder = Color(0x40FFFFFF)
    val TextPrimary = Color(0xFFF1F5FF)
    val TextSecondary = Color(0xB3F1F5FF)
}

private val DarkScheme = darkColorScheme(
    primary = KytheraColors.Cyan,
    secondary = KytheraColors.Violet,
    background = KytheraColors.Midnight,
    surface = KytheraColors.Midnight,
    onPrimary = KytheraColors.Midnight,
    onSecondary = KytheraColors.Midnight,
    onBackground = KytheraColors.TextPrimary,
    onSurface = KytheraColors.TextPrimary
)

private val LightScheme = lightColorScheme(
    primary = Color(0xFF0F766E),
    secondary = Color(0xFF6D28D9)
)

@Composable
fun KytheraTheme(
    darkTheme: Boolean = true, // identitas visual KYTHERA: gelap sebagai default
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkScheme else LightScheme,
        content = content
    )
}
