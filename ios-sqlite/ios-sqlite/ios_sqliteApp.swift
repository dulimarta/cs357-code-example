//
//  ios_sqliteApp.swift
//  ios-sqlite
//
//  Created by Hans Dulimarta on 10/6/25.
//

import SwiftUI

@main
struct ios_sqliteApp: App {
    init() {
        DatabaseManager.initialize()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
