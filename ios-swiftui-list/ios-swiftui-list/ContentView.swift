//
//  ContentView.swift
//  ios-swiftui-list
//
//  Created by Hans Dulimarta on 11/11/24.
//

import SwiftUI

struct ContentView: View {
    @StateObject var vm = MyViewModel()
    @State var selectedIndex: Int?
    var body: some View {
        VStack {
            Text("You have \(vm.names.count) names below")
            vm.selectedPerson.map {
                Text("You select \($0.firstName)")
            }
            List(selection: $vm.selectedUUID) {
                ForEach(vm.names, id: \.self) { s in
                }
//                ForEach(vm.persons) { p in
//                    Text("\(p.firstName) \(p.lastName)")
//                }
                .onDelete(perform: removeMe)
            }.listStyle(.plain)
        }
        .padding()
    }
    
    func removeMe(at pos: IndexSet) {
        vm.removeAt(pos)
        
    }
}

#Preview {
    ContentView()
}
