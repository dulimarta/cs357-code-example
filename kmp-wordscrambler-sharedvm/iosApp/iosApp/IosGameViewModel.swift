import Foundation
import Shared

@MainActor
@Observable
class IosGameViewModel : ObservableObject  {
    private let commonVm: GameViewModel
    private(set) var secret: NSString?
    private(set) var letters: Array<String?>
    private(set) var removedLetter: String?
    private(set) var solved: Bool
    init(commonVm: GameViewModel) {
        commonVm.generateNewWord()
        self.commonVm = commonVm
        self.secret = commonVm.secretWord.value
        self.letters = []
        self.removedLetter = nil
        self.solved = false
        commonVm.secretWord.subscribe(
            scope: commonVm.viewModelScope,
            onValue: { [weak self] in
                self?.secret = $0
            })
        commonVm.letters.subscribe(
            scope: commonVm.viewModelScope,
            onValue: { [weak self] in
                self?.letters = $0 as? [String?] ?? []
            })
        commonVm.removedLetter.subscribe(
            scope: commonVm.viewModelScope,
            onValue: { [weak self] in
                self?.removedLetter = $0 as String?
            }
        )
        commonVm.solved.subscribe(
            scope: commonVm.viewModelScope,
            onValue: {
                self.solved = Bool($0)
            })
    }
    deinit {
        self.commonVm.clear()
    }

    func generateNewWord() {
        self.commonVm.generateNewWord()
    }

    func clickAt(pos: Int) {
        self.commonVm.clickAtPos(pos: Int32(pos))
    }
}
