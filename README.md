# Breadcrumb: Android Application

Android and Google Firebase application to keep your friends and family updated with your locations during your travels.

The application allows a traveller to save a "breadcrumb" with the current location, time, and a custom description. Friends and family with the app receive a notification and are able to view previous breadcrumbs.

Inspired by the fairy tale [Hansel and Gretel](https://en.wikipedia.org/wiki/Hansel_and_Gretel), children who drop breadcrumbs while following a witch into the woods so they can find their way back out.

## Setup

### Android

Install [Android Studio](https://developer.android.com/studio/) and import the project.

### Firebase: Realtime Database

Breadcrumb requires a Firebase Realtime Database to store and share information.

First, modify the package name so you can attach your own database:
* In `app/build.gradle`, change the value of `android.defaultConfig.applicationId`
* In `app/src/main`, change the package name to match the new name

[Create a project in Google Cloud.](https://cloud.google.com/resource-manager/docs/creating-managing-projects)

Follow [these instructions](https://firebase.google.com/docs/database/android/start?authuser=0) to create a Firebase Realtime Database, using your newly changed package name.

Credentials will be generated for you and downloaded as the file `google-services.json`. Place this file in the project root directory.

To view and manage your Database from the online portal, go to the [Firebase Realtime Database console](https://console.firebase.google.com/).


When you build and run the Android application, it will connect to your new database. Data structures will be created on the fly as required.

## Creating a Production Release

### Step 1: Building a Release Variant

Create a new file in the root directory to hold the secure keystore details:
```shell
cp keystore.properties.template keystore.properties
```

This file will be ignored by Git. Set your keystore information in this file.

Under _Build_, click _Select Build Variant_.

In the _Build Variants_ view which appears, change the **Active Build Variant** from _debug_ to _release_.

Build the application normally to your phone. The keystore credentials will be verified and applied.

#### Step 2: Creating the Signed App

Under _Build_, click _Generate Signed Bundle / APK_.

Follow the instructions, inputting your keystore path and credentials, to create a signed app which can be uploaded to the app store.

___

## Work In Progress

### Fonts

In `app/src/main/res/font/`:
* Add the `.ttf` files for your preferred fonts

In `app/src/main/res/values/styles.xml`:
* Configure `PrimaryFont` and `PrimaryFontBold` to use the new fonts

In `app/src/main/res/values/colors.xml`:
* Change the colors to the preferred style
