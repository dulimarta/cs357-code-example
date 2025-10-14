//
//  ContentView.swift
//  ios-sqlite
//
//  Created by Hans Dulimarta on 10/6/25.
//

import SwiftUI

struct ContentView: View {
    
    @ObservedObject var vm = SimpleViewModel()
    
    var body: some View {
        VStack(alignment: .listRowSeparatorLeading) {
            Text("SQLite Example \(vm.names.count)").font(.title)
            Button("Add Name") {
//                Task {
                    vm.addName()
//                }
            }
            List {
                ForEach(vm.names.enumerated(), id: \.offset) { pos, guest in
                    HStack(alignment: .center) {
                        Text(guest.firstName + " " + guest.lastName)
                        Spacer()
                        Image(systemName: "trash").onTapGesture {
                            vm.deleteNameAt(pos: pos)
                        }

                    }.frame(maxWidth: .infinity)
                }
            }
        }
        .padding()
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity,
            alignment: .topLeading
        )
        .background(Color.mint)
    }
}

#Preview {
    ContentView()
}
