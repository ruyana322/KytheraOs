package os.kythera.desktop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import os.kythera.ui.theme.KytheraColors

enum class AppId { BROWSER, SETTINGS, ABOUT }

data class DesktopApp(
    val id: AppId,
    val label: String,
    val glyph: String,
    val accent: Color
)

val DesktopApps: List<DesktopApp> = listOf(
    DesktopApp(AppId.BROWSER, "Browser", "B", KytheraColors.Cyan),
    DesktopApp(AppId.SETTINGS, "Settings", "S", KytheraColors.Violet),
    DesktopApp(AppId.ABOUT, "About", "i", Color(0xFFFBBF24))
)

fun appFor(id: AppId): DesktopApp = DesktopApps.first { it.id == id }

/**
 * State desktop: aplikasi yang terbuka (urutan = urutan di taskbar),
 * aplikasi yang sedang tampil (null = desktop terlihat), dan status launcher.
 */
class DesktopState {
    val openApps = mutableStateListOf<AppId>()
    var focused by mutableStateOf<AppId?>(null)
    var launcherOpen by mutableStateOf(false)

    fun open(id: AppId) {
        if (id !in openApps) openApps.add(id)
        focused = id
        launcherOpen = false
    }

    fun close(id: AppId) {
        openApps.remove(id)
        if (focused == id) focused = openApps.lastOrNull()
    }

    fun minimize() {
        focused = null
    }

    fun toggleFromTaskbar(id: AppId) {
        focused = if (focused == id) null else id
        launcherOpen = false
    }

    fun showDesktop() {
        focused = null
        launcherOpen = false
    }

    fun toggleLauncher() {
        launcherOpen = !launcherOpen
    }
}
