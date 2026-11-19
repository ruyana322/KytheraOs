package os.kythera.desktop

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import os.kythera.ui.theme.KytheraColors

/** Wallpaper prosedural: gradasi gelap dengan dua cahaya lembut. Tanpa aset gambar. */
@Composable
internal fun Wallpaper(modifier: Modifier = Modifier) {
    Canvas(modifier.fillMaxSize()) {
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(KytheraColors.Midnight, KytheraColors.DeepViolet),
                start = Offset.Zero,
                end = Offset(size.width, size.height)
            )
        )
        glow(KytheraColors.Cyan, Offset(size.width * 0.12f, size.height * 0.18f), size.maxDimension * 0.55f, 0.22f)
        glow(KytheraColors.Violet, Offset(size.width * 0.88f, size.height * 0.82f), size.maxDimension * 0.60f, 0.26f)
    }
}

private fun DrawScope.glow(color: Color, center: Offset, radius: Float, alpha: Float) {
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(color.copy(alpha = alpha), Color.Transparent),
            center = center,
            radius = radius
        ),
        radius = radius,
        center = center
    )
}
