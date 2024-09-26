//
//  GreenViewModel.swift
//  ios-navigation-data
//
//  Created by Hans Dulimarta on 3/20/24.
//

import Foundation
import Combine

class GreenViewModel {
    @Published var sum: Float
    @Published var product: Float

    init() {
        self.sum = 0
        self.product = 0
    }
    
    func initializeData(first f: Float, second s: Float) {
        self.sum = f + s
        self.product = f * s
    }
}
