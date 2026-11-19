package os.kythera.desktop

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import os.kythera.ui.theme.KytheraColors

/** Layar utama desktop: wallpaper, ikon desktop, jendela, launcher, dan taskbar. */
@Composable
fun DesktopScreen() {
    val state = remember { DesktopState() }

    BackHandler(enabled = state.launcherOpen || state.focused != null) {
        if (state.launcherOpen) state.launcherOpen = false else state.minimize()
    }

    Box(Modifier.fillMaxSize()) {
        Wallpaper()

        Box(
            Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.displayCutout)
        ) {
            // Ikon desktop
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DesktopApps.forEach { app ->
                    DesktopIcon(app) { state.open(app.id) }
                }
            }

            // Jendela: semua aplikasi terbuka tetap ada di komposisi, hanya yang fokus ditampilkan.
            state.openApps.forEach { id ->
                key(id) {
                    AnimatedVisibility(
                        visible = state.focused == id,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 28.dp, end = 28.dp, top = 20.dp, bottom = 100.dp),
                        enter = fadeIn() + scaleIn(initialScale = 0.94f),
                        exit = fadeOut() + scaleOut(targetScale = 0.94f)
                    ) {
                        AppWindow(
                            app = appFor(id),
                            onMinimize = { state.minimize() },
                            onClose = { state.close(id) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            // Scrim: ketuk di luar launcher untuk menutup
            if (state.launcherOpen) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { state.launcherOpen = false }
                )
            }

            AnimatedVisibility(
                visible = state.launcherOpen,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 92.dp),
                enter = fadeIn() + slideInVertically { it / 6 } + scaleIn(initialScale = 0.96f),
                exit = fadeOut() + slideOutVertically { it / 6 }
            ) {
                LauncherPanel(onOpen = { state.open(it) })
            }

            Taskbar(
                state = state,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun DesktopIcon(app: DesktopApp, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(84.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTile(app, 56.dp)
        Spacer(Modifier.height(6.dp))
        Text(
            text = app.label,
            color = KytheraColors.TextPrimary,
            fontSize = 12.sp,
            maxLines = 1
        )
    }
}
