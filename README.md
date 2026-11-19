# KYTHERA OS

Bring the desktop experience to Android.

Status: **Phase 2 – Desktop Interface** (v0.1.0). Desktop workspace, taskbar, launcher, dan jendela aplikasi sudah ada. Browser (WebView) baru dimulai di Phase 3; jendela Browser saat ini masih placeholder.

## Cara build lewat GitHub (tanpa komputer)

1. Push seluruh isi folder ini ke branch `main` (termasuk folder `.github`).
2. Buka tab **Actions** → workflow **Build APK** → **Run workflow** (atau cukup push ke `main`).
3. Setelah hijau, buka run tersebut dan unduh artifact **kythera-os-debug-apk**.
4. Ekstrak zip-nya, lalu instal `app-debug.apk` di POCO X6 Pro (izinkan instalasi dari sumber tidak dikenal).

## Struktur

```
.github/workflows/build.yml          CI: build debug APK + upload artifact
app/src/main/java/os/kythera/
  MainActivity.kt                    mode imersif + DesktopScreen
  AppInfo.kt                         versi aplikasi
  desktop/                           workspace, taskbar, launcher, window
  ui/theme/                          warna dan tema
gradle/libs.versions.toml            version catalog
```

Masih satu modul `app`. Pemisahan menjadi `desktop`, `browser`, dst. dilakukan saat ada kode nyata yang dipisah.

## Versi toolchain

AGP 8.7.3 · Gradle 8.9 · Kotlin 2.0.21 · Compose BOM 2024.10.01 · JDK 17 · minSdk 31 (Android 12) · compileSdk/targetSdk 35
