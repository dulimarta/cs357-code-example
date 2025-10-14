//
//  DatabaseManager.swift
//  ios-sqlite
//
//  Created by Hans Dulimarta on 10/7/25.
//

import Foundation
import GRDB

// The shared singleton instance
var AppDatabase: DatabaseManager!

class DatabaseManager {
    // The database queue grants synchronized access to the database.
    let dbQueue: DatabaseQueue
    
    static func initialize() {
        AppDatabase = try! DatabaseManager()
    }
    
    func getInstance() -> DatabaseManager {
        return self //.dbQueue
    }
    
    private init() throws {
        // Find the database file path in the application support directory
        let fileManager = FileManager.default
        let appSupportURL = try fileManager.url(
            for: .applicationSupportDirectory,
            in: .userDomainMask,
            appropriateFor: nil,
            create: true
        )
        let databaseURL = appSupportURL.appendingPathComponent("db.sqlite")
        
        // Open the database connection
        dbQueue = try DatabaseQueue(path: databaseURL.path)
        
        // Apply any necessary database migrations
        try migrator.migrate(dbQueue)
    }
    
    
    // Define the database migrations
    private var migrator: DatabaseMigrator {
        var migrator = DatabaseMigrator()
        
        // v1: Create the initial schema
        migrator.registerMigration("createV1Tables") { db in
            try db.create(table: "guest") { t in
                t.autoIncrementedPrimaryKey("id")
                t.column("firstName", .text).notNull()
                t.column("lastName", .text).notNull()
            }
        }
        
        return migrator
    }
}

extension DatabaseManager {
    func save(_ guest: Guest) async throws {
        try await dbQueue.write { db in
            try guest.save(db)
        }
    }
    
    func remove(_ guest: Guest) async throws {
        try await dbQueue.write { db in
            try guest.delete(db)
        }
    }
    
    func search(byName name: String) async throws -> [Guest] {
        try await dbQueue.read { db in
            try Guest.fetchAll(db, sql: "SELECT * FROM guest WHERE name = ?", arguments: [name])
        }
    }
}
