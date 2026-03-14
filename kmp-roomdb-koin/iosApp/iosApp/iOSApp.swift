import UIKit
import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        KoinHelperFuncsKt.initialize()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}