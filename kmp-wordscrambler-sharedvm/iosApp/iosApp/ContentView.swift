import SwiftUI
import Shared

struct ContentView: View {
    @StateObject  var vm = IosGameViewModel(commonVm: GameViewModel())
    var body: some View {
        VStack {
            Button("New Word") {
                vm.generateNewWord()
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .buttonStyle(.bordered)
            ZStack {
                if let rem = vm.removedLetter {
                    BigLetter(rem)
                        .offset(y: -50.0)
                }
                HStack(spacing: 0) {
                    ForEach(vm.letters.indices, id: \.self) { pos in
                        BigLetter(vm.letters[pos])
                            .onTapGesture {
                                vm.clickAt(pos: pos)
                            }
                    }
                }
                if (vm.solved) {
                    Text("You solved it!!").font(.system(size: 30))
                        .offset(y: +50)
                }
            }
            .frame(maxHeight: .infinity)


        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
    }
}

struct BigLetter: View {
    private var letter: String?
    init (_ ch: String?) {
        self.letter = ch
    }
    var body: some View {
        let sz = if self.letter == nil { 20.0 } else {48.0}
        let bgColor = if self.letter == nil {Color.white} else {Color.green}
        let corner = if self.letter == nil { 4.0} else {12.0}
        Text(self.letter ?? "")
            .font(.system(size: 24.0))
            .frame(width: sz, height:sz)
            .background(bgColor)
            .cornerRadius(corner)
            .overlay(RoundedRectangle(cornerRadius: corner)
                         .stroke(.black, lineWidth: 2.0))
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
