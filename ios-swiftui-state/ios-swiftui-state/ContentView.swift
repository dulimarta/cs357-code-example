//
//  ContentView.swift
//  ios-swiftui-state
//
//  Created by Hans Dulimarta on 4/16/24.
//

import SwiftUI

struct ContentView: View {
    @State var xOffset = 0.0
    var body: some View {
        VStack {
            Text("Hello, World!")
                .offset(x: xOffset)
            Button("Animate") {
                withAnimation(
                    Animation.easeInOut(duration: 0.1).repeatCount(5))
                    {
                        xOffset += 20
                    }
                    completion: {
                        xOffset = 0
                    }
            }
        }
//        .padding()
        .background(Color.red)
    }
}
struct ColorView: View {
    @State var redLevel: Double
    @State var greenLevel: Double
    @State var blueLevel: Double
    
    init() {
        redLevel = Double.random(in: 0...1)
        greenLevel = Double.random(in: 0...1)
        blueLevel = Double.random(in: 0...1)
    }
    
    var body:  some View {
        VStack(alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/) {
            Text("Color is \(redLevel.dec),\(greenLevel.dec),\(blueLevel.dec)")
                .foregroundColor(Color(red: 1 - redLevel, green: 1-greenLevel, blue: 1 - blueLevel))
            VStack(spacing: 20.0) {
                Slider(
                    value: $redLevel,
                    in: 0...1,
                    minimumValueLabel: Text("R"),
                    maximumValueLabel: Text("")
                ) {
                }
                Slider(value: $greenLevel,
                       in: 0...1, minimumValueLabel: Text("G"), maximumValueLabel: Text("")) {}
                Slider(value: $blueLevel, in: 0...1, minimumValueLabel: Text("B"), maximumValueLabel: Text("")) {}
            }
            .frame(width: 300)
            
        }
        .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, maxHeight: .infinity)
        .background(Color(red: redLevel, green: greenLevel, blue: blueLevel))
    }
}

#Preview {
    ContentView()
}

extension Double {
    var dec: String {
        return String(format: "%.2f", self)
    }
}
