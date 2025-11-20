# Beacon Simulator

An Android application that simulates iBeacon broadcasts for testing and development of proximity-based applications.

## Overview

Beacon Simulator allows developers to test beacon detection features without requiring physical beacon hardware. The app uses Bluetooth Low Energy (BLE) advertising to broadcast iBeacon signals with configurable parameters.

## Features

- 📡 **iBeacon Broadcasting**: Simulates Apple iBeacon protocol
- 🎛️ **Configurable Parameters**: Set custom UUID, Major, and Minor values
- 🔄 **Start/Stop Controls**: Easy toggle for broadcast control
- ✅ **Input Validation**: Validates UUID format and numeric fields
- 📊 **Real-time Status**: Displays current broadcast status and errors
- 📱 **Device Compatibility Check**: Verifies BLE advertising support

## Prerequisites

- **Android Device**: Physical device with BLE advertising support (emulator doesn't support BLE advertising)
- **Android Version**: API 26 (Android 8.0) or higher
- **Bluetooth**: Device must support Bluetooth Low Energy advertising

## Default Configuration

The app comes pre-configured with default values:
- **UUID**: `2f234454-cf6d-4a0f-adf2-f4911ba9ffa6`
- **Major**: `1`
- **Minor**: `1`
- **Transmission Power**: `-59 dBm`
- **Manufacturer Code**: `0x0118` (Apple)

## Installation

1. Clone the repository
2. Open the `BeaconSimulator` project in Android Studio
3. Build and run on a physical Android device

```bash
cd BeaconSimulator
./gradlew assembleDebug
```

## Usage

1. **Launch the app** on your Android device
2. **Configure beacon parameters**:
   - Enter a valid UUID (or use the default)
   - Set Major and Minor values (0-65535)
3. **Start Broadcasting**: Tap "Start Broadcasting"
4. **Stop Broadcasting**: Tap "Stop Broadcasting" when done

### Testing with Museum Art Guide

This simulator is designed to work with the Museum Art Guide app:
1. Start the Beacon Simulator with the default UUID
2. Launch the Museum Art Guide app
3. The guide app will detect the beacon and display art information

## Technical Details

### Dependencies

- **AltBeacon Library**: Android Beacon Library for iBeacon support
- **AndroidX Core**: Core Android libraries
- **AppCompat**: Backward compatibility support

### Permissions

The app requires the following permissions:
- `BLUETOOTH`: Basic Bluetooth functionality
- `BLUETOOTH_ADMIN`: Bluetooth administration
- `BLUETOOTH_ADVERTISE`: BLE advertising (API 31+)

### iBeacon Protocol

The app implements the iBeacon standard:
- **Layout**: `m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24`
- **Manufacturer Code**: `0x0118` (Apple)
- **UUID**: 16-byte identifier
- **Major/Minor**: 2-byte identifiers for grouping

## Troubleshooting

### "Device does not support BLE Advertising"
- Your device doesn't support BLE advertising
- Try a different device with BLE advertising capability

### UUID Format Error
- Ensure UUID follows standard format: `xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx`
- Use lowercase hexadecimal characters

### Broadcast Not Detected
- Verify Bluetooth is enabled
- Check that the receiving app is scanning for beacons
- Ensure both devices are within range (~30 meters)
- Check that UUIDs match between broadcaster and receiver

## Development

### Project Structure

```
BeaconSimulator/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/beaconsimulator/
│   │   │   └── MainActivity.kt          # Main activity
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml    # UI layout
│   │   │   └── values/
│   │   │       ├── strings.xml
│   │   │       └── themes.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
└── build.gradle.kts
```

### Building from Source

```bash
# Clone the repository
git clone https://github.com/ksprabhatkumar/MuseumArtGuide.git
cd MuseumArtGuide/BeaconSimulator

# Build the project
./gradlew build

# Install on connected device
./gradlew installDebug
```

## License

This project is part of the Museum Art Guide system.

## Related Projects

- **Museum Art Guide**: The companion app that detects beacons and displays art information

## Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.

## Support

For issues or questions, please open an issue on the GitHub repository.
