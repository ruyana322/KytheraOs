package os.kythera.desktop

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import os.kythera.ui.theme.KytheraColors
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/** Taskbar mengambang: launcher, tombol desktop, aplikasi terbuka, dan area status. */
@Composable
internal fun Taskbar(state: DesktopState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .glass(RoundedCornerShape(28.dp), TaskbarFill)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        TaskbarButton(selected = state.launcherOpen, onClick = { state.toggleLauncher() }) {
            Text(
                text = "K",
                color = KytheraColors.Cyan,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        TaskbarButton(selected = false, onClick = { state.showDesktop() }) {
            Canvas(Modifier.size(18.dp)) {
                drawRoundRect(
                    color = KytheraColors.TextPrimary,
                    cornerRadius = CornerRadius(4.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }

        if (state.openApps.isNotEmpty()) {
            TaskbarDivider()
            state.openApps.forEach { id ->
                TaskbarAppButton(
                    app = appFor(id),
                    focused = state.focused == id,
                    onClick = { state.toggleFromTaskbar(id) }
                )
            }
        }

        TaskbarDivider()
        StatusArea()
    }
}

@Composable
private fun TaskbarButton(
    selected: Boolean,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) Color(0x33FFFFFF) else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Composable
private fun TaskbarAppButton(app: DesktopApp, focused: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 5.dp, vertical = 3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTile(app, 28.dp)
        Spacer(Modifier.height(3.dp))
        Box(
            Modifier
                .width(if (focused) 16.dp else 6.dp)
                .height(3.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(if (focused) KytheraColors.Cyan else KytheraColors.TextSecondary)
        )
    }
}

@Composable
private fun TaskbarDivider() {
    Box(
        Modifier
            .width(1.dp)
            .height(28.dp)
            .background(KytheraColors.GlassBorder)
    )
}

@Composable
private fun StatusArea() {
    val now by rememberNow()
    val battery by rememberBatteryPercent()
    val wide = LocalConfiguration.current.screenWidthDp >= 720
    val timeFormat = remember { DateTimeFormatter.ofPattern("HH:mm") }
    val dateFormat = remember { DateTimeFormatter.ofPattern("EEE, d MMM") }

    Column(
        modifier = Modifier.padding(start = 4.dp, end = 6.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = now.format(timeFormat),
            color = KytheraColors.TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        val detail = buildList {
            if (wide) add(now.format(dateFormat))
            if (battery >= 0) add("$battery%")
        }.joinToString(" · ")
        if (detail.isNotEmpty()) {
            Text(text = detail, color = KytheraColors.TextSecondary, fontSize = 11.sp)
        }
    }
}

/** Jam sistem; diperbarui tepat saat pergantian menit. */
@Composable
private fun rememberNow(): State<LocalDateTime> =
    produceState(initialValue = LocalDateTime.now()) {
        while (true) {
            value = LocalDateTime.now()
            delay((60 - value.second) * 1000L)
        }
    }

/** Persentase baterai dari broadcast sistem; -1 jika belum diketahui. */
@Composable
private fun rememberBatteryPercent(): State<Int> {
    val context = LocalContext.current
    val level = remember { mutableIntStateOf(-1) }

    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(c: Context, intent: Intent) {
                level.intValue = batteryPercent(intent)
            }
        }
        val sticky = ContextCompat.registerReceiver(
            context,
            receiver,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
        if (sticky != null) level.intValue = batteryPercent(sticky)
        onDispose { context.unregisterReceiver(receiver) }
    }
    return level
}

private fun batteryPercent(intent: Intent): Int {
    val raw = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
    val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
    return if (raw < 0 || scale <= 0) -1 else raw * 100 / scale
}
