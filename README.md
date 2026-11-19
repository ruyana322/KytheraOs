# KYTHERA OS

Bring the desktop experience to Android.

Status: **Phase 1 – Foundation** (v0.1.0). Belum ada fitur browser; layar saat ini hanya placeholder untuk membuktikan build berjalan.

## Cara build lewat GitHub (tanpa komputer)

1. Buat repo kosong di GitHub, lalu unggah seluruh isi folder ini (termasuk folder `.github`).
2. Buka tab **Actions** → workflow **Build APK** → **Run workflow** (atau cukup push ke branch `main`).
3. Setelah hijau, buka run tersebut dan unduh artifact **kythera-os-debug-apk**.
4. Ekstrak zip-nya, lalu instal `app-debug.apk` di POCO X6 Pro (izinkan instalasi dari sumber tidak dikenal).

## Struktur

```
.github/workflows/build.yml   CI: build debug APK + upload artifact
app/                          satu modul Android (Compose)
gradle/libs.versions.toml     version catalog
```

Phase 1 sengaja memakai satu modul `app`. Pemisahan menjadi `core`, `desktop`, `browser`, dst. dilakukan saat ada kode nyata yang dipisah (Phase 2–4).

## Versi toolchain

AGP 8.7.3 · Gradle 8.9 · Kotlin 2.0.21 · Compose BOM 2024.10.01 · JDK 17 · minSdk 31 (Android 12) · compileSdk/targetSdk 35
