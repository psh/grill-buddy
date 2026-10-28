# Contributing to Grill Buddy

Thank you for your interest in contributing to Grill Buddy! This guide will help you get the project set up and running locally.

## Prerequisites

- **Java Development Kit (JDK)**: Java 8 or higher
- **Android SDK**: Install via Android Studio or the command-line tools
- **Gradle**: Included via the Gradle wrapper (`gradlew`)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/psh/grill-buddy.git
cd grill-buddy
```

### 2. Set Up Android SDK

If you don't have the Android SDK installed, you can:
- Install [Android Studio](https://developer.android.com/studio) (recommended)
- Or install the [Android Command Line Tools](https://developer.android.com/studio#command-tools)

Set the `ANDROID_HOME` environment variable to point to your Android SDK installation:

```bash
export ANDROID_HOME=/path/to/android/sdk
```

### 3. Build the Project

Use the Gradle wrapper to build the project:

```bash
./gradlew build
```

### 4. Run Tests

```bash
./gradlew test
```

### 5. Install and Run on Device/Emulator

First, ensure you have an Android device connected or an emulator running, then:

```bash
./gradlew installDebug
```

Or use Android Studio to run the app directly.

## Code Style

This project follows standard Android development conventions:

- **Indentation**: 4 spaces (configured in `.editorconfig`)
- **Line endings**: Unix-style (LF)
- **Encoding**: UTF-8

We recommend using an editor that supports [EditorConfig](https://editorconfig.org/) to automatically apply these settings.

## Making Changes

1. Create a new branch for your feature or fix
2. Make your changes following the code style guidelines
3. Test your changes thoroughly
4. Commit with clear, descriptive messages
5. Push your branch and open a pull request

## Questions?

Feel free to open an issue if you have any questions or run into problems getting the project set up.
