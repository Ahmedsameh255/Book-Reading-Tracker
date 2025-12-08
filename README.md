# 📚 Book Reading Tracker - Mobile Computing Project

This project includes:
- A **native Android app** built with Kotlin
- A **Flutter app** for cross-platform UI
- Integration between Flutter and Android via intents
- Unit tests with JUnit & Mockito
- UI tests with Espresso

## 📁 Repository Structure

Book-Reading-Tracker/
├── AndroidApp/                    # Native Android app
│   ├── app/src/main/java/com/example/booktrackerapp/
│   │   ├── MainActivity.kt        # Main screen with Flutter intent button
│   │   ├── AddBookActivity.kt
│   │   ├── BookDetailActivity.kt
│   │   ├── Book.kt
│   │   ├── BookDatabase.kt
│   │   ├── BookDao.kt
│   │   ├── BookValidator.kt
│   │   ├── BookCalculator.kt
│   │   └── ...
│   ├── app/src/test/              # JUnit & Mockito tests
│   ├── app/src/androidTest/       # Espresso tests
│   └── build.gradle
│
├── FlutterApp/                    # Flutter app (book_stats_flutter)
│   ├── lib/main.dart              # Simple Flutter UI
│   ├── android/app/src/main/AndroidManifest.xml
│   ├── pubspec.yaml
│   └── README.md                  # Flutter setup instructions
│
│
└── README.md                      # This file

## 🚀 Getting Started

### Android App
Open `AndroidApp/` in Android Studio.

### Flutter App
Open `FlutterApp/` in VS Code or Android Studio with Flutter plugin.

## 🧪 Testing
Run tests in `AndroidApp/app/src/test/` and `AndroidApp/app/src/androidTest/`.
