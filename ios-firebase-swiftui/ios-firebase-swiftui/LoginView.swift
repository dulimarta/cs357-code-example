//
//  LoginView.swift
//  ios-firebase-swiftui
//
//  Created by Hans Dulimarta on 10/27/25.
//

import SwiftUI

struct LoginView: View {
    @Environment(\.dismiss) var dismiss
    @State var email: String = "me@test.com"
    @State var password: String = "1234567"
    @State var message: String? = nil
    let vm: MyViewModel
    
    init(vm: MyViewModel) {
        self.vm = vm
    }
    var body: some View {
        VStack {
            TextField("Email", text: $email)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            SecureField("Password", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            if let message { Text(message)}
            HStack {
                Button("Login") {
                    Task {
                        let result = await vm.authenticate(email: email, password: password)
                        switch result {
                        case .success(let uid):
                            dismiss()
                        case .failure(let failure):
                            message = failure.localizedDescription
                        }
                    }
                }
                Button("Cancel") {
                    dismiss()
                }
            }
            //            .font(.title)
            //            .padding()
            //            .background(.black)
        }
    }
}

//#Preview {
//    LoginView()
//}
