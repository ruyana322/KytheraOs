package os.kythera.desktop

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import os.kythera.ui.theme.KytheraColors

/** Panel launcher yang muncul di atas taskbar. */
@Composable
internal fun LauncherPanel(onOpen: (AppId) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(340.dp)
            .glass(RoundedCornerShape(28.dp), LauncherFill)
            .padding(20.dp)
    ) {
        Text(
            text = "Applications",
            color = KytheraColors.TextSecondary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(12.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(88.dp),
            modifier = Modifier.heightIn(max = 280.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(DesktopApps, key = { it.id }) { app ->
                LauncherItem(app) { onOpen(app.id) }
            }
        }
    }
}

@Composable
private fun LauncherItem(app: DesktopApp, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTile(app, 52.dp)
        Spacer(Modifier.height(6.dp))
        Text(
            text = app.label,
            color = KytheraColors.TextPrimary,
            fontSize = 12.sp,
            maxLines = 1
        )
    }
}
