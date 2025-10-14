//
//  Guest.swift
//  ios-sqlite
//
//  Created by Hans Dulimarta on 10/7/25.
//

import Foundation
import GRDB

// FetchableRecord: needed by fetchAll(), fetchOne(), fetchCursor()
// PersitableRecord: needed by insert(), upsert(), save()
// TableRecord: needed by update(), select(), filter()
struct Guest: Codable, Identifiable, FetchableRecord, PersistableRecord, TableRecord {
    var id: Int64?
    var firstName: String
    var lastName: String
}
