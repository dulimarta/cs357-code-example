# Build Instruction

The Android examples are compiled using Android Studio Electric Eel (which uses 
Android Gradle Plugin 7.4). If you have an older version of Android Studio you may 
have to downgrade AGP to version 7.3 (or older) by editing the top-level `build.gradle`

In case you receive the following error:
Unsupported Modules Detected: Compilation is not supported for following modules:.
Unfortunately you can't have non-Gradle Java modules and Android-Gradle modules in one
project.

1. Close the project
2. Clouse Android Studio
3. Delete the `.idea` directory
4. Delete all `.iml` files
5. Reopen the project in Android Studio.
   
Reference: [StackOverflow](https://stackoverflow.com/questions/28668252/android-studio-error-unsupported-modules-detected-compilation-is-not-supported)

Also try to "Invalidate Caches" in Android Studio.

Expect to see "Invalid JDK Path" or "Invalid Gradle Path" error message when loading Android examples. 
JDK settings (and path settings in general) are unique per user/laptop.
