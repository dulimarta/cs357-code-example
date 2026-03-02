//
// Created by Hans Dulimarta on 2/25/26.
//

import Foundation
import SwiftUI
import UIKit
import ComposeApp

struct DualView : View{
    var body: some View {
        Text("SwiftUI Dual view")
    }
}
class  iosSwiftUIViewFactory: SwiftUIViewFactory {
    func getSwiftUIView() -> Any {
        return UIHostingController(rootView: DualView())
    }
}