//
//  MainVC.swift
//  ios-livedata
//
//  Created by Hans Dulimarta on 3/18/24.
//

import UIKit
import Combine
import SnackBar

class MainVC: UIViewController {

    weak var liveCounterLabel: UILabel!
    @IBOutlet weak var counterLabel: UILabel!
    @IBOutlet weak var addBtn: UIButton!
    
    let VM = MainViewModel()
    var pool: Set<AnyCancellable> = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = .yellow
        self.liveCounterLabel = self.view.viewWithTag(41) as? UILabel
        VM.$counter
            .receive(on: DispatchQueue.main)
            .sink { val in
                self.counterLabel.text = "Counter: \(val)"
            }
            .store(in: &pool)
        
        VM.liveCounter.observe { val in
            self.liveCounterLabel.text = "Live Counter: \(val!)"            
        }
        
        VM.countingenabled.observe { enable in
            if enable! == false {
                self.addBtn.isEnabled = false
                SnackBar.make(in: self.view, message: "Counting disabled", duration: .lengthShort)
                    .show()
            }
        }
    }
    

    @IBAction func addOneClicked(_ sender: Any) {
        VM.addOne()
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
