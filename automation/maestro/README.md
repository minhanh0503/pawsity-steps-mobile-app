# Maestro Automated Test Flows

## Overview

For our UI testing we chose to leverage Maestro Studio given prior work experience with setting it up in an internship Kevin had worked. This is an automated E2E UI testing software that uses instructions written in `.yaml` to work through the user flows that we dictate. We demonstrate the entire participant user flow from beginning to end using the "ABC" order.

## Usage

In order to run this test suite, you must have Maestro Studio installed, as well as the application running in Android Studio. From there by running `User Flow.yaml`, it will run the script through the emulated image or connected device if using an Android device instead of emulation.