package os.kythera.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import os.kythera.ui.theme.KytheraColors

internal val TaskbarFill = Color(0xB3101436)
internal val LauncherFill = Color(0xE6101436)
internal val WindowFill = Color(0xF20D1130)

/** Permukaan "glass": isi semi-transparan + garis tepi tipis. */
internal fun Modifier.glass(shape: Shape, fill: Color): Modifier =
    this
        .clip(shape)
        .background(fill)
        .border(1.dp, KytheraColors.GlassBorder, shape)

/** Ikon aplikasi: kotak rounded dengan huruf. Orisinal, tidak meniru ikon OS lain. */
@Composable
internal fun AppTile(app: DesktopApp, size: Dp, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(size * 0.28f)
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(app.accent.copy(alpha = 0.18f))
            .border(1.dp, app.accent.copy(alpha = 0.55f), shape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = app.glyph,
            color = app.accent,
            fontSize = (size.value * 0.42f).sp,
            fontWeight = FontWeight.Medium
        )
    }
}
