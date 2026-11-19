package os.kythera.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import os.kythera.ui.theme.KytheraColors

/**
 * Layar placeholder Phase 1. Hanya untuk membuktikan bahwa proyek, Compose,
 * dan pipeline build berjalan. Desktop workspace dibuat di Phase 2.
 */
@Composable
fun FoundationScreen(versionName: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(KytheraColors.Midnight, KytheraColors.DeepViolet)
                )
            )
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(28.dp))
                .background(KytheraColors.GlassFill)
                .border(1.dp, KytheraColors.GlassBorder, RoundedCornerShape(28.dp))
                .padding(horizontal = 48.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "KYTHERA OS",
                color = KytheraColors.TextPrimary,
                fontSize = 34.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 6.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Foundation build · v$versionName",
                color = KytheraColors.Cyan,
                fontSize = 14.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Bring the desktop experience to Android",
                color = KytheraColors.TextSecondary,
                fontSize = 12.sp
            )
        }
    }
}
