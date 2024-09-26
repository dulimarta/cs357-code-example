//
//  MainViewModel.swift
//  ios-livedata
//
//  Created by Hans Dulimarta on 3/18/24.
//

import Foundation
class MainViewModel {
    
    @Published var counter = 0
    let liveCounter = LiveData(0)
    let countingenabled = LiveData(true)
    
    func addOne() {
        counter += 1
        liveCounter.value! += 1
        if counter > 13 {
            countingenabled.value = false
        }
    }
}
