# Unsend Detector

Unsend Detector is an Android application that detects and displays deleted (unsent) Instagram messages using the system's Notification Listener and Usage Stats APIs. The app captures messages from bubble notifications before they are deleted and stores them locally.

## Features

- Detects deleted messages from Instagram chats.
- Displays the deleted messages in a chat-style interface.
- Organizes deleted messages by sender.
- Requires minimal permissions (Notification Access and Usage Stats).
- Modern and clean user interface.
- Supports swipe-to-refresh on user list.

## How It Works

1. The app listens to Instagram notifications using a `NotificationListenerService`.
2. It extracts messages from the `android.messages` field inside bubble-style notifications.
3. When a message is no longer present and Instagram wasn't opened recently, the app assumes it was deleted and saves it.
4. Deleted messages are saved in a local Room database and presented in a conversation-style UI.

## Permissions Required

- **Notification Access**: To read Instagram notifications in real time.
- **Usage Stats Access**: To determine whether Instagram was opened recently and prevent false positives.

## Technologies Used

- Kotlin & Android Jetpack
- View Binding
- Room Database
- NotificationListenerService
- UsageStatsManager
- Material Design 3

## Installation

1. Clone the repository.
2. Open in Android Studio.
3. Build and run on a device with Android 10+.
4. Grant notification access and usage access as prompted.

## Limitations

- Only works with Instagram bubble notifications.
- May not capture every deleted message if the app does not receive the notification (e.g., if Instagram was in use at the same moment).
- Does not support other messaging apps (e.g., WhatsApp, Messenger).

## Disclaimer

This app does not intercept or store messages from Instagram directly. It only observes system notifications and does not use any private APIs or credentials. The app’s functionality depends entirely on what the operating system provides via public notification access.

## License

This project is intended for academic purposes. Not for commercial use.

[![Watch the video](https://img.youtube.com/vi/sqSuuHmXu-4/hqdefault.jpg)](https://youtu.be/sqSuuHmXu-4)

