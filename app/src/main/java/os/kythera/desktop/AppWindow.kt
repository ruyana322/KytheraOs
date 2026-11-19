package os.kythera.desktop

import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import os.kythera.AppInfo
import os.kythera.ui.theme.KytheraColors

/** Jendela aplikasi bergaya desktop: title bar + tombol minimize/close + konten. */
@Composable
internal fun AppWindow(
    app: DesktopApp,
    onMinimize: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.glass(RoundedCornerShape(22.dp), WindowFill)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0x14FFFFFF))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppTile(app, 26.dp)
            Spacer(Modifier.width(10.dp))
            Text(
                text = app.label,
                color = KytheraColors.TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.weight(1f))
            WindowButton(onClick = onMinimize) {
                val y = size.height / 2f
                drawLine(
                    color = KytheraColors.TextPrimary,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            Spacer(Modifier.width(6.dp))
            WindowButton(onClick = onClose) {
                val stroke = 2.dp.toPx()
                drawLine(
                    color = KytheraColors.TextPrimary,
                    start = Offset.Zero,
                    end = Offset(size.width, size.height),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = KytheraColors.TextPrimary,
                    start = Offset(size.width, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            when (app.id) {
                AppId.BROWSER -> Placeholder(
                    title = "KYTHERA Browser",
                    body = "The desktop browsing engine (WebView) arrives in Phase 3."
                )
                AppId.SETTINGS -> Placeholder(
                    title = "Settings",
                    body = "Browser and system settings arrive in Phase 4."
                )
                AppId.ABOUT -> AboutContent()
            }
        }
    }
}

@Composable
private fun WindowButton(onClick: () -> Unit, draw: DrawScope.() -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.size(12.dp), onDraw = draw)
    }
}

@Composable
private fun Placeholder(title: String, body: String) {
    Column {
        Text(
            text = title,
            color = KytheraColors.TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(Modifier.height(8.dp))
        Text(text = body, color = KytheraColors.TextSecondary, fontSize = 14.sp)
    }
}

@Composable
private fun AboutContent() {
    Column {
        Text(
            text = "KYTHERA OS",
            color = KytheraColors.TextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 4.sp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Bring the desktop experience to Android",
            color = KytheraColors.Cyan,
            fontSize = 13.sp
        )
        Spacer(Modifier.height(16.dp))
        InfoRow("Version", AppInfo.VERSION_NAME)
        InfoRow("Device", "${Build.MANUFACTURER} ${Build.MODEL}")
        InfoRow("Android", "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})")
        InfoRow("Web engine", "Android WebView (not yet integrated)")
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            color = KytheraColors.TextSecondary,
            fontSize = 13.sp,
            modifier = Modifier.width(96.dp)
        )
        Text(text = value, color = KytheraColors.TextPrimary, fontSize = 13.sp)
    }
}
