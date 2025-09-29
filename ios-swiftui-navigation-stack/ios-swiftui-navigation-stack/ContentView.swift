//
//  ContentView.swift
//  ios-swiftui-navigation-stack
//
//  Created by Hans Dulimarta on 11/4/24.
//

import SwiftUI

struct ContentView: View {
    @ObservedObject private var navCtrl = MyNavigator()
    @StateObject var orderViewModel = OrderViewModel()
    var body: some View {
        NavigationStack(path: $navCtrl.navPath) {
            SelectItemsView(viewModel: orderViewModel) {
                navCtrl.navigate(to: .ShoppingCart)
            }
            .navigationDestination(for: Route.self) { dest in
                switch dest {
                case .ShoppingCart:
                    let selected:String? = navCtrl.currentPayloadGet(key: "ADDR")
                    let payment:String? = navCtrl.currentPayloadGet(key: "PAY")
                    ShoppingCartView(viewModel: orderViewModel,
                                     shipAddr: selected, paymentMethod: payment) {
                        navCtrl.navigate(to: Route.SelectShippingAddress)
                    } onSelectPayment: { a in
                        navCtrl.navigate(to: .SelectPaymentMethod)
                    } onCheckout: {
                        navCtrl.navigate(to: .Checkout)
                    }
                case .SelectShippingAddress:
                    SelectShippingAddressView(
                    ) { addr in
                        navCtrl.previousPayloadSet(key: "ADDR", value: addr)
                        navCtrl.navigateBack()
                    }
                    
                case .SelectPaymentMethod:
                    SelectPaymentView(totalCharge: 100.0) { pay in
                        navCtrl.previousPayloadSet(key: "PAY", value: pay)
                        navCtrl.navigateBack()
                    }
                case .Checkout:
                    CheckoutView() { confirmed in
                        navCtrl.navigateBackToRoot()
                    }
                }
            }
        }
    }
    
}

#Preview {
    ContentView()
}
