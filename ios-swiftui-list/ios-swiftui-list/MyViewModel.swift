//
//  MyViewModel.swift
//  ios-swiftui-list
//
//  Created by Hans Dulimarta on 11/11/24.
//

import SwiftUI
import Fakery
let faker = Faker(locale:"en_US")
struct Person: Identifiable, Hashable {
    let id = UUID()
    let firstName: String
    let lastName: String
}

class MyViewModel: ObservableObject {
    @Published var persons: Array<Person> = []
    @Published var names: Array<String> = []
    @Published var selectedPerson: Person?
    
    var selectedUUID: UUID? {
        didSet {
            selectedPerson = self.persons.first { p in
                p.id == selectedUUID
            }
        }
    }
    
    init() {
        for _ in 0..<50 {
            let p = Person(firstName: faker.name.firstName(), lastName: faker.name.lastName())
            self.persons.append(p)
            self.names.append(p.firstName + " " + p.lastName)
        }
    }
    
    func removeAt(_ p: IndexSet) {
        let victim = p.map {
            self.persons[$0].id
        }
        if let uid = selectedUUID, victim.contains(uid) {
            selectedUUID = nil
        }
        self.persons.remove(atOffsets: p)
    }
}
