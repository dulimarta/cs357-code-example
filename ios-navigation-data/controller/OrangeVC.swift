//
//  OrangeVC.swift
//  ios-navigation-data
//
//  Created by Hans Dulimarta on 3/19/24.
//

import UIKit
import Combine

class OrangeVC: UIViewController {

    @IBOutlet weak var infoLabel: UILabel!
    let myViewModel = OrangeViewModel()
    var pool: Set<AnyCancellable> = []
    
    init(arg: OrangeArg? = nil) {
        super.init(nibName: nil, bundle: nil)
        if let delivery = arg {
            print("Incoming data is \(delivery)")
            myViewModel.city = delivery.city
            myViewModel.distance = delivery.distance
        }
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = .orange
        // Do any additional setup after loading the view.
        myViewModel.$city.sink { city in
            
            self.infoLabel.text = "City is \(city)"
        }.store(in: &pool)
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
