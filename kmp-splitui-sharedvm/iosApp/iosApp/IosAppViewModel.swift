//
//  IosAppViewModel.swift
//  iosApp
//
//  Created by Hans Dulimarta on 1/6/26.
//

import Shared

@MainActor
class IosAppViewModel: ObservableObject {
    private let commonVm: AppViewModel

    @Published private(set) var count: KotlinInt

    init(commonVm: AppViewModel) {
        self.commonVm = commonVm
        self.count = commonVm.count.value
        commonVm.count.subscribe(
            scope: commonVm.viewModelScope,
            onValue: { [weak self] in
                self?.count = $0
            }
        )
    }

    // Must call clear to cancel coroutines and close any resources
    deinit {
        self.commonVm.clear()
    }

    func add() {
        self.commonVm.add()
    }
}
