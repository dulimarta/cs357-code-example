//
//  SelectPaymentView.swift
//  ios-swiftui-navigation-stack
//
//  Created by Hans Dulimarta on 9/26/25.
//

import SwiftUI

struct SelectPaymentView: View {
    var totalCharge: Float
    var onPaymentSelected: (String) -> Void
    init(totalCharge: Float, onPaymentSelected: @escaping (String) -> Void) {
        self.totalCharge = totalCharge
        self.onPaymentSelected = onPaymentSelected
    }
    var body: some View {
        Text("Select Payment").font(.title)
        HStack {
            Button("PayPay") {
                self.onPaymentSelected("PayPay")
            }
            Button("Visa") {
                self.onPaymentSelected("Visa")
            }
        }
        .buttonStyle(.borderedProminent)
    }
}

#Preview {
    SelectPaymentView(totalCharge: 100.0) {a in
    }
}
