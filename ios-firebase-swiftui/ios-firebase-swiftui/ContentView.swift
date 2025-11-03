//
//  ContentView.swift
//  ios-firebase-swiftui
//

import SwiftUI

struct ContentView: View {
    @State var vm: MyViewModel = .init()
    @State var showLogin: Bool = false
    var body: some View {
        VStack {
            HStack(spacing: 20) {
                Text("Number of documents: \(vm.allStudents.count)")
                Button("Add") {
                    vm.addNewMember()
                }.disabled(vm.uid == nil)
                if vm.uid != nil {
                    Button("Logout") {
                        vm.signOut()
                    }

                } else {
                    Button("Login") {
                        showLogin = true
                    }
                    .sheet(isPresented: $showLogin) {
                        LoginView(vm: vm)
                    }
                }
            }
            
            LazyVStack(alignment: .leading, spacing: 4) {
                ForEach(vm.allStudents, id: \.id) { m in
                    Text("\(m.firstName) \(m.lastName)")
                        .padding([.bottom, .top, .leading], 4)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .background(
                            Color.blue.opacity(0.3),
                            in: RoundedRectangle(
                                cornerSize: CGSize(width: 8, height:8),
                                style: .circular
                            )
                        )
                }
            }.frame(
                maxWidth: .infinity,
                maxHeight: .infinity,
                alignment: .topLeading
            )
        }
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity,
            alignment: .topLeading
        )
        .padding()
    }
}

#Preview {
    ContentView()
}
