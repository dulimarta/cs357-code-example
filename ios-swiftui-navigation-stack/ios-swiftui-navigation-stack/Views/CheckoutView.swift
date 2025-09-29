//
//  CheckoutView.swift
//  ios-swiftui-navigation-stack
//
//  Created by Hans Dulimarta on 9/28/25.
//

import SwiftUI

struct CheckoutView: View {
    private var onCheckOut: (Bool) -> Void
    init(onCheckOut: @escaping (Bool) -> Void) {
        self.onCheckOut = onCheckOut
    }
    var body: some View {
        VStack {
            Text("Checkout").font(.title)
            HStack {
                Button("Confirm") {
                    onCheckOut(true)
                }
                Button("Cancel") {
                    onCheckOut(false)
                }
            }.buttonStyle(.borderedProminent)
            
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

#Preview {
    CheckoutView() { c in }
}
