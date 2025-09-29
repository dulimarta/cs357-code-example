//
//  SelectShippingAddressView.swift
//  ios-swiftui-navigation-stack
//
//  Created by Hans Dulimarta on 9/24/25.
//

import SwiftUI

struct SelectShippingAddressView: View {
    var onShipTo: (String) -> Void
    init(onShipTo: @escaping (String) -> Void) {
        self.onShipTo = onShipTo
    }
    var body: some View {
        Text("Select Shipping Address")
        Button("Allendale") {
            onShipTo("49401")
        }.buttonStyle(.borderedProminent)
    }
}

#Preview {
    @Previewable @State var x: String? = nil
    SelectShippingAddressView() {a in }
}
