//
//  File.swift
//  ios-sqlite
//
//  Created by Hans Dulimarta on 10/6/25.
//

import Foundation
import Combine
import Fakery
import GRDB

class SimpleViewModel: ObservableObject {
    let appDB = AppDatabase.getInstance()
    @Published private(set) var names: [Guest] = []
    
    let dataFaker = Faker(locale: "en")
    private var dbObserver: DatabaseCancellable?
    
    init()  {
        let observation = ValueObservation.tracking(Guest.fetchAll)
        self.dbObserver = .none
        let obs = observation.start(in: appDB.dbQueue, scheduling: .immediate)
        { err in
            print("Error in observer \(err)")
        } onChange: {  (gg: [Guest]) in
            Task {
                await MainActor.run  {
                    self.names = gg
                }
            }
        }
        self.dbObserver = obs
    }
    
    deinit {
        dbObserver?.cancel()
    }
    
    func addName() {
        Task {
            let fName = dataFaker.name.firstName()
            let lName = dataFaker.name.lastName()
            try! await appDB.save(Guest(firstName: fName, lastName: lName))
        }
    }
    
    func deleteNameAt(pos: Int) {
        let nameToDelete = names.remove(at: pos)
        Task {
            try! await appDB.remove(nameToDelete)
            await MainActor.run {
                
            }
        }
    }
}
