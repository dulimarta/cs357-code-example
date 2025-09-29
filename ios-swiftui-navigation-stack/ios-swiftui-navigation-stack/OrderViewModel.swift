//
//  OrderViewModel.swift
//  ios-swiftui-navigation-stack
//
//  Created by Hans Dulimarta on 9/26/25.
//

import Foundation

class OrderViewModel: ObservableObject
{
    @Published private(set) var items: [String] = []
    
    func addOrders(items: [String]) {
        self.items.removeAll()
        self.items.append(contentsOf: items)
    }
}
