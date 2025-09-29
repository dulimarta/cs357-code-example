//
//  SelectItemsView.swift
//  ios-swiftui-navigation-stack
//
//  Created by Hans Dulimarta on 9/24/25.
//

import SwiftUI

struct SelectItemsView: View {
    @ObservedObject var vm: OrderViewModel
    let onPlaceOrder: () -> Void
    init(viewModel: OrderViewModel, onPlaceOrder: @escaping () -> Void) {
        self.onPlaceOrder = onPlaceOrder
        self._vm = ObservedObject(wrappedValue: viewModel)
    }
    var body: some View {
        VStack(alignment: .leading) {
            Text("This is select items")
                .font(.title)
            Button("Place Order") {
                vm.addOrders(items: ["Avocado", "Banana", "Cayene Pepper"])
                onPlaceOrder()
            }.buttonStyle(.borderedProminent)

        }
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity,
            alignment: .topLeading
        )
        .padding()
        .background(Color.yellow)
    }
}

//#Preview {
//    SelectItemsView(viewModel: <#T##OrderViewModel#>, onPlaceOrder: <#T##() -> Void#>) {
//        
//    }
//}
