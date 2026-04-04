This is a Kotlin Multiplatform that demonstrate how to access Audio Devices.
Since both platform uses different classes and design strategies, the approach
used in this example is to express the tasks of an AudioController as
the following interface:

```kotlin
interface AudioController {
    fun startRecording()
    fun stopRecording()
    fun playbackRecording()
}
```

and have each platform implements its own platform specific class.

This design idea is borrowed from [KMP Record](https://github.com/theolm/kmp-record).
However, a significant amount of details have been removed to make it easier for
you to understand the overall structure.
