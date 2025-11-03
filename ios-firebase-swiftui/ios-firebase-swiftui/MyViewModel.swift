//
//  MyViewModel.swift
//  ios-firebase-swiftui
//
//  Created by Hans Dulimarta on 10/27/25.
//

import Foundation
import FirebaseFirestore
import FirebaseAuth
import Fakery

@Observable
class MyViewModel {
    let db = Firestore.firestore()
    let auth = Auth.auth()
    let fake = Faker.init()
    
    private(set) var allStudents: Array<Student> = []
    private(set) var uid: String? = nil
    
    init() {
        db.collection("members").addSnapshotListener { (querySnapshot, error) in
            guard let memberColl = querySnapshot else {
                print("Error fetching collections \(error!)")
                return
            }
            do {
                for chg in  memberColl.documentChanges {
                    var m = try chg.document.data(as: Student.self)
                    switch chg.type {
                    case .added:
                        self.allStudents.append(m)
                        //                        print("New document: \(m)")
                    case .modified:
                        print("Modified document: \(m)")
                    case .removed:
                        print("Removed document: \(m)")
                        self.allStudents.removeAll { $0.id == m.id }
                    }
                }
            } catch {
                print("Error decoding Firebase document: \(error)")
            }
        }
    }
    
    func addNewMember() {
        let member = Student(
            firstName: fake.name.firstName(),
            lastName: fake.name.lastName()
        )
        Task {
            try db.collection("members").addDocument(from: member)
        }
    }
    func authenticate(email e: String, password p: String) async -> Result<String, Error> {
        do {
            let authResult = try await auth.signIn(withEmail: e, password: p)
            self.uid = authResult.user.uid
            return .success(authResult.user.uid)
        } catch {
            return .failure(error)
        }
    }
    
    func signOut() {
        try! auth.signOut()
        self.uid = nil
    }
}
