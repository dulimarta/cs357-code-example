import SwiftUI
import Shared

struct ContentView: View {
    @State private var showContent = false
    @StateObject var vm = IosAppViewModel(commonVm: AppViewModel())
    var body: some View {
        VStack {
            Button("Click me: \(vm.count)") {
                vm.add()
            }.buttonStyle(.borderedProminent)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
