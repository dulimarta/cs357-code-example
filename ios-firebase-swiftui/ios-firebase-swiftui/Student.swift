//
//  Member.swift
//  ios-firebase-swiftui
//
//  Created by Hans Dulimarta on 10/27/25.
//

import Foundation
import FirebaseFirestore

struct Student: Codable, Identifiable {
    @DocumentID var id: String?
    var firstName: String
    var lastName: String
}
