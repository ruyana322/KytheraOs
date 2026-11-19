package os.kythera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import os.kythera.ui.FoundationScreen
import os.kythera.ui.theme.KytheraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KytheraTheme {
                FoundationScreen(versionName = BuildConfigInfo.VERSION_NAME)
            }
        }
    }
}

/** Versi ditulis konstan agar tidak perlu mengaktifkan BuildConfig di Phase 1. */
private object BuildConfigInfo {
    const val VERSION_NAME = "0.1.0"
}
