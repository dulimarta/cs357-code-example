//
//  ShoppingCartView.swift
//  ios-swiftui-navigation-stack
//
//  Created by Hans Dulimarta on 9/24/25.
//

import SwiftUI

struct ShoppingCartView: View {
    @ObservedObject var vm: OrderViewModel
    private var onSelectAddress: () -> Void
    private var onSelectPayment: (Float) -> Void
    private var onCheckout: () -> Void
    private var shipAddr: String?
    private var paymentMethod: String?
    init(
        viewModel: OrderViewModel,
        shipAddr: String? = nil,
        paymentMethod: String? = nil,
        onSelectAddress: @escaping () -> Void,
        onSelectPayment: @escaping (Float) -> Void,
        onCheckout: @escaping () -> Void
    ) {
        self.onSelectAddress = onSelectAddress
        self.onSelectPayment = onSelectPayment
        self.onCheckout = onCheckout
        self._vm = ObservedObject(wrappedValue: viewModel)
        self.shipAddr = shipAddr
        self.paymentMethod = paymentMethod
    }
    var body: some View {
        VStack(alignment: .leading) {
            Text("Shopping Cart").font(.title)
            ForEach(vm.items, id: \.self) { item in
                Text(item)
            }
            if let shipAddr {
                Text("Shipping to: \(shipAddr)")
            }
            if let paymentMethod {
                Text("Pay with: \(paymentMethod)")
            }
            HStack(alignment:.center) {
                Button("Address") {
                    onSelectAddress()
                }
                Button("Payment") {
                    onSelectPayment(100.00)
                }
                Button("Checkout") {
                    onCheckout()
                }
                .disabled(shipAddr == nil  || paymentMethod == nil)
            }
            .frame(maxWidth: .infinity, alignment: .init(horizontal: .center, vertical: .center))
            .buttonStyle(.borderedProminent)
        }
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity,
            alignment: .topLeading
        )
        .padding()
//        .background(Color.orange)
    }
}

#Preview {
    ShoppingCartView(
        viewModel: OrderViewModel(),
        onSelectAddress: {
        },
        onSelectPayment: {a in print("Selected address: \(a)")},
        onCheckout: {})
}
