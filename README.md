# VinylStream

VinylStream is an Android application written in Java designed to stream audio from vinyl records to Google Cast-supported devices throughout your home. This project combines traditional audio systems with modern streaming technology, utilizing a Raspberry Pi and a USB analog-to-digital converter to digitize and broadcast audio.

## Features

- **Analog to Digital Conversion**: Converts vinyl record audio from analog to digital using a USB converter.
- **Local Streaming**: Utilizes a Raspberry Pi to create an HTTP accessible audio stream within a local network.
- **Android Application**: Includes a custom-built Android app in Java that casts audio to multiple Google Home devices, allowing seamless streaming across different rooms.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them:

- Raspberry Pi, any model with internet will work. I used a Zero W
- USB Analog-to-Digital Converter
- Android Studio for app development
- Google Home devices or any Google Cast-supported speakers

### Installing



1. **Set up your Raspberry Pi**:
    - Flash a suitable Linux distribution (e.g., Raspbian) onto your Raspberry Pi.
    - Install and configure Icecast and DarkIce on the Raspberry Pi to handle audio streaming. Detailed instructions can be found [here](https://maker.pro/raspberry-pi/projects/how-to-build-an-internet-radio-station-with-raspberry-pi-darkice-and-icecast).

2. **Prepare the Android application**:
    - Clone this repository to your local machine.
    - Open the project in Android Studio.
    - Replace `STREAM_URL` and `CAST_APPLICATION_ID` in `BuildConfig` with your actual stream URL and Google Cast application ID.

3. **Connect your USB converter**:
    - Connect the USB analog-to-digital converter to your Raspberry Pi.
    - Connect the audio output of your vinyl player to the converter.

4. **Run the Android app**:
    - Build the application in Android Studio and run it on your Android device.
    - Ensure your Android device and Google Home devices are on the same network as your Raspberry Pi.

## Usage

Start playing a vinyl record, open the VinylStream app on your Android device, and connect to your home speakers through the app interface. You should hear the vinyl audio being streamed throughout your house.

