# Mobile Apps

Две реализации MovieAgent:

- `ios implementation/IOSImplementation.xcodeproj` — UIKit, Swift, iOS 16+.
- `android/` — Kotlin, Jetpack Compose, Material 3, Android 8+ (API 26).

## iOS

Открой `ios implementation/IOSImplementation.xcodeproj` в Xcode, выбери симулятор и нажми Run.
Для запуска на своём iPhone выбери Team в Signing & Capabilities.
Стартовый экран: `iosimplementation/screens/ChatViewController.swift`.

## Android

Открой папку `android` в Android Studio, дождись Gradle Sync и нажми Run.
Используй встроенную JDK Android Studio (Settings → Build Tools → Gradle → Gradle JDK).
Точка входа: `app/src/main/java/com/example/androidimplementation/MainActivity.kt`. Экраны лежат в `screens/chat` и `screens/history` внутри того же пакета.
Сборка из терминала: `cd android` → `./gradlew assembleDebug` (JDK 17–22).

Идентификаторы `com.example.iosimplementation` и `com.example.androidimplementation` пока временные.
