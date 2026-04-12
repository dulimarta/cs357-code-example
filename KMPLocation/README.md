This is a Kotlin Multiplatform project targeting Android, iOS that shows
how to get the current device location (via its GPS receiver).

The Android code is pretty much straighforward requiring only 6 lines of code.
The iOS counterpart is longer, since it has to interface with the
CLLocationManager class and all its delegate function

Unfortunately, the Swift/ObjC delegate functions are designed to return
asynchronous results as callback, but the idiomatic Kotlin approach is
to use `suspend`. Bridging the two asynchronous styles is accomplished
using Kotlin SuspendableCoroutine.

This example borrows some concepts from (and a simplified implementation
of) the technique presented in a Medium article by [Ngenge Senior](https://ngengesenior.medium.com/requesting-the-current-location-of-ios-device-from-kotlin-using-compose-multiplatform-f7f0b6f6bbc4)

