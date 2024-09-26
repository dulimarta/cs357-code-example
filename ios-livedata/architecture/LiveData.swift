//
//  LiveData.swift
//  ios-livedata
//
//  Created by Hans Dulimarta on 3/18/24.
//

import Foundation

typealias ObserverFunc<T> = (T?) -> Void

class LiveData<T> {
    private var _value: T?
    private var subscribers: Array<ObserverFunc<T>> = []

    init(_ value: T? = nil) {
        self._value = value
    }
    
    // Declare a property with setter and getter
    var value: T? {
        set(newValue) {
            self._value = newValue
            for fn in self.subscribers {
                // Notify each subscriber of the new value
                fn(newValue)
            }
        }
        get {
            return self._value
        }
    }
    
    /* must use post() when updating from a non-UI thread */
    func post(newValue: T?) {
        self._value = newValue
        DispatchQueue.main.async {
            for fn in self.subscribers {
                // Notify each subscriber of the new value
                fn(newValue)
            }

        }
    }
    
    func observe(_ obs: @escaping ObserverFunc<T>) {
        self.subscribers.append(obs)
        obs(self._value)
    }
}
