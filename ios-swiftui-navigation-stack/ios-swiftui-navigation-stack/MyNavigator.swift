//
//  MyNavigator.swift
//  ios-swiftui-navigation-stack
//
//  Created by Hans Dulimarta on 11/4/24.
//

import SwiftUI
enum Route: Hashable {
    case ShoppingCart
    case SelectShippingAddress
    case SelectPaymentMethod
    case Checkout
}

class MyNavigator: ObservableObject {
    @Published var navPath: [Route] = []
    // payload is a stack for exchanging data between a parent screen
    // and its immediate child screen
    var payload: Array<[String:Any]> = []
    
    func navigate(to dest: Route) {
        navPath.append(dest)
        payload.append([:])
    }
    
    func navigateBack() {
        navPath.removeLast()
        payload.removeLast()
    }
    
    func navigateBackToRoot() {
        navPath.removeAll()
        payload.removeAll()
    }
    
    func navigateBackUntil(d: Route, inclusive: Bool) {
        if navPath.isEmpty {
            return
        }
        navPath.removeLast()
        
        while navPath.last != d && !navPath.isEmpty {
             navPath.removeLast()
        }
        if inclusive && !navPath.isEmpty {
            navPath.removeLast()
        }
    }

    func previousPayloadSet<T>(key:String, value: T) {
        payload[payload.endIndex-2][key] = value
    }
    
    func currentPayloadGet<T>(key:String) -> T {
        let lastItem = payload.last!
        return lastItem[key] as! T
    }
    
}
