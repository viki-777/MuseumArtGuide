# Museum Art Guide

An Android application that uses iBeacon technology to provide contextual information about museum art pieces based on visitor proximity.

## Overview

Museum Art Guide is a proximity-aware mobile application that automatically displays relevant art descriptions when visitors approach specific exhibits equipped with Bluetooth beacons. The app provides a seamless, hands-free museum experience.

## Features

- 🎨 **Proximity-Based Content**: Automatically displays art information when near beacons
- 📍 **Real-time Detection**: Continuous beacon scanning for instant updates
- 🔍 **Smart Grace Period**: Prevents content flickering with 10-scan grace period
- 📱 **Clean UI**: Simple, distraction-free interface for art appreciation
- 🔋 **Optimized Scanning**: Efficient Bluetooth scanning for battery preservation
- 📊 **Debug Logging**: Comprehensive logging for troubleshooting

## Prerequisites

- **Android Device**: Physical device with Bluetooth Low Energy (BLE) support
- **Android Version**: API 26 (Android 8.0) or higher
- **Bluetooth**: Must be enabled for beacon detection
- **Location Permission**: Required for Bluetooth scanning on Android

## Installation

1. Clone the repository
2. Open the `MuseumArtGuide` project in Android Studio
3. Build and run on a physical Android device

```bash
cd MuseumArtGuide
./gradlew assembleDebug
```

## Usage

### For Museum Visitors

1. **Launch the app** when entering the museum
2. **Grant permissions** (Bluetooth and Location)
3. **Approach exhibits**: Content appears automatically when near beacon-equipped art
4. **Read and explore**: Information updates as you move through the museum

### Testing with Beacon Simulator

For development and testing:
1. Launch the Beacon Simulator app on a second device
2. Start broadcasting with UUID: `2f234454-cf6d-4a0f-adf2-f4911ba9ffa6`
3. Launch Museum Art Guide
4. Bring devices close together to trigger content display

## Configuration

### Target Beacon

The app is configured to detect the following beacon:
- **UUID**: `2f234454-cf6d-4a0f-adf2-f4911ba9ffa6`
- **Protocol**: iBeacon (Apple)
- **Layout**: `m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24`

### Scanning Parameters

- **Foreground Scan Period**: 1100ms
- **Foreground Between Scan Period**: 0ms
- **Grace Period**: 10 scans (~11 seconds)

### Art Content

Currently displays information about:
> **Shrimant Maharaja Madhav Rao Scindia** - A compassionate leader with a progressive and egalitarian mindset that defined the Hindavi Swaraj his ancestors fought for. The gallery includes a collection of art, photographs, and artifacts related to him.

## Technical Details

### Dependencies

- **AltBeacon Library**: Android Beacon Library for iBeacon detection
- **AndroidX Core**: Core Android libraries
- **AppCompat**: Backward compatibility support
- **Lifecycle Components**: For observing beacon data

### Permissions

Required permissions:
- `BLUETOOTH`: Basic Bluetooth functionality
- `BLUETOOTH_ADMIN`: Bluetooth administration
- `BLUETOOTH_SCAN`: BLE scanning (API 31+)
- `BLUETOOTH_CONNECT`: Bluetooth connections (API 31+)
- `ACCESS_FINE_LOCATION`: Required for Bluetooth scanning
- `ACCESS_COARSE_LOCATION`: Location access

### Architecture

The app uses:
- **BeaconManager**: Manages beacon scanning lifecycle
- **RegionViewModel**: Observes beacon ranging data
- **Observer Pattern**: Reactive updates to beacon proximity changes
- **Grace Period Logic**: Prevents rapid content switching

## How It Works

1. **Initialization**: BeaconManager starts when app launches
2. **Scanning**: Continuous BLE scanning for nearby beacons
3. **Detection**: Matches detected beacons against target UUID
4. **Display**: Shows art description when target beacon is found
5. **Grace Period**: Waits 10 scans before hiding content when beacon is lost
6. **Reset**: Returns to "Searching..." state after grace period expires

### State Flow

```
App Start → Scanning → Beacon Found → Show Content
                ↓                           ↓
          No Beacons Found            Beacon Lost
                ↓                           ↓
          Keep Scanning          Grace Period (10 scans)
                                             ↓
                                    Hide Content → Scanning
```

## Development

### Project Structure

```
MuseumArtGuide/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/museumartguide/
│   │   │   └── MainActivity.kt           # Main activity
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml     # UI layout
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
cd MuseumArtGuide/MuseumArtGuide

# Build the project
./gradlew build

# Install on connected device
./gradlew installDebug
```

### Debugging

The app includes comprehensive logging with tag `BeaconDebug`:
- Beacon ranging updates
- Detection events
- Grace period counting
- State changes

Enable verbose logging in Logcat:
```bash
adb logcat -s BeaconDebug
```

## Customization

### Adding New Beacons

Edit `MainActivity.kt`:
```kotlin
private const val TARGET_BEACON_UUID = "your-uuid-here"
private const val ART_DESCRIPTION = "Your art description here"
```

### Adjusting Grace Period

Modify the grace period constant:
```kotlin
private val GRACE_PERIOD_COUNT = 10  // Change to desired scan count
```

### Scan Timing

Adjust scan parameters:
```kotlin
beaconManager.foregroundScanPeriod = 1100L      // Scan duration
beaconManager.foregroundBetweenScanPeriod = 0L  // Gap between scans
```

## Troubleshooting

### App Not Detecting Beacons

1. **Check Bluetooth**: Ensure Bluetooth is enabled
2. **Grant Permissions**: Verify all permissions are granted
3. **Check UUID**: Confirm beacon UUID matches target UUID
4. **Verify Beacon**: Ensure beacon is broadcasting (test with Beacon Simulator)
5. **Check Distance**: Move closer to the beacon (within 10 meters)

### Content Flickering

- Grace period prevents this by waiting 10 scans before hiding content
- Increase `GRACE_PERIOD_COUNT` if flickering persists

### Battery Drain

- App is optimized with efficient scan parameters
- Consider increasing `foregroundBetweenScanPeriod` for less frequent scanning

## Future Enhancements

- [ ] Support for multiple beacons with different art pieces
- [ ] Database integration for dynamic content management
- [ ] Image display alongside text descriptions
- [ ] Audio guide integration
- [ ] Multi-language support
- [ ] Museum floor maps with beacon locations
- [ ] Analytics and visitor tracking (privacy-conscious)

## License

This project is part of the Museum Art Guide system.

## Related Projects

- **Beacon Simulator**: Companion app for testing beacon detection without physical hardware

## Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.

## Support

For issues or questions, please open an issue on the GitHub repository.

## Acknowledgments

- AltBeacon library for robust beacon detection
- Museum staff for content and requirements
- The Scindia dynasty history for the featured exhibit content
