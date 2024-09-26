//
//  GreenVC.swift
//  ios-navigation-data
//
//  Created by Hans Dulimarta on 3/19/24.
//

import UIKit
import Combine

class GreenVC: UIViewController {

    let greenVM = GreenViewModel()
    var subscriberPool: Set<AnyCancellable> = []
    
    @IBOutlet weak var infoLabel: UILabel!
    @IBOutlet weak var sumLabel: UILabel!
    @IBOutlet weak var productLabel: UILabel!
    
    private var arg: GreenArg?
    var reverseUpdate: ((GreenReverseArg)-> Void)?
    
    init(arg: GreenArg?) {
        super.init(nibName: nil, bundle: nil)
        if let incomingNumbers = arg {
            greenVM.initializeData(first: incomingNumbers.first, second: incomingNumbers.second)
            self.arg = incomingNumbers
        }
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = .green
        
        greenVM.$sum.sink { val in
            self.sumLabel?.text = "Sum = \(val)"
        }
        .store(in: &subscriberPool)
        
        greenVM.$product.sink { val in
            self.productLabel?.text = "Product = \(val)"
        }
        .store(in: &subscriberPool)
        
        // Do any additional setup after loading the view.
    }

    override func viewWillAppear(_ animated: Bool) {
        if let incomingNumbers = self.arg {
            infoLabel.text = "Incoming numbers: \(incomingNumbers.first) and \(incomingNumbers.second)"
        }

    }

    @IBAction func deliverResult(_ sender: Any) {
        let result = GreenReverseArg(sum: greenVM.sum, product: greenVM.product)
        self.reverseUpdate? (result)
        self.navigationController?.popViewController(animated: true)
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
