# Cosmo Devices Assessment

Technologies used: 
- MVVM for having a layering approach
- Hilt for dependency injection
- Clean Architecture with use cases
- For navigation: Jetpack navigation components with graphs
- For UI: Jetpack Compose (only one exception is MainActivity because I wanted to create the graph through a FragmentContainerView) 
- Room for caching API results (Single source of thruth)
- Handled no internet connection case 
- Bluetooth android native implementation for scanning and connecting. When a device is clicked we show in a toast it's respective macAddress. 

# Screens

## List of devices (Main screen)
- Showing a list of devices retrieved from API, stored in Room database, and accessed through Room. 
- Screen states: Loading, Success, Error.
- Clicking on a device navigates to Device Details screen.
- Is having a bottom navigation which redirects user to Scan Bluetooth devices screen.

## Device details 
- Showing device details in a Grid.
- Each property item can be clicked to expand and to see the full content. 

## Bluetooth devices
- Showing a list of Paired devices and a list of Scanned devices
- User can start scanning, stop scanning and start a server to pair with another device.

# Known possible improvements:
- Handling of bluetooth permission currently handles just the good path, where user is accepting it. This can be improved by handling also the permission denial.
- Some @Compose files contains more components, they can be splitted into separated files for better visibility.
- Currently, just the viewmodels are unit tested, but we can add tests for more classes, such as the BluetoothController.
- Project is having hardcoded Strings and dimens which can be migrated to resources and accessed from there.
- Application is treating currently just the Light mode, night colors can be improved.

# How to use
- Preferrably, the device to be in Light mode.
- Bluetooth activated and permissions accepted. 

Demo video: https://www.youtube.com/shorts/kvs-3h1s8ns 

Project was done using: Android Studio Giraffe | 2022.3.1 Patch 3
