//
//  MainVC.swift
//  ios-navigation-data
//
//  Created by Hans Dulimarta on 3/19/24.
//

import UIKit
import SnackBar

class MainVC: UIViewController {
    
    @IBOutlet weak var cityInput: UITextField!
    @IBOutlet weak var firstNum: UITextField!
    @IBOutlet weak var secondNum: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = .lightGray
        //        self.title = "Main Screen"
        self.navigationItem.title = "Main Screen"
        //        self.navigationItem.leftBarButtonItem = UIBarButtonItem(title: "What", style: .plain, target: nil, action: nil)
        // Do any additional setup after loading the view.
    }
    
    @IBAction func toBlue(_ sender: Any) {
        let bluecheese = BlueVC()
        self.navigationController?.pushViewController(bluecheese, animated: true)
    }
    
    @IBAction func toOrange(_ sender: Any) {
        let payload = OrangeArg(city: cityInput.text ?? "local", distance: 213.5)
        let orangeChicken = OrangeVC(arg: payload)
        self.navigationController?.pushViewController(orangeChicken, animated: true)
    }
    
    func reverseArgReceived(_ a:GreenReverseArg) {
        SnackBar.make(in: self.view,
                      message: "Product is \(a.product)",
                      duration: .lengthLong)
        .show()
    }
    
    @IBAction func toGreen() {
        // Confirm text input to Float
        let firstFloat: Optional<Float> = Float(firstNum.text!)
        // must unwrap firstFloat when it is passed to GreenArg
        let payload = GreenArg(first: firstFloat!, second: Float(secondNum.text!)!)
        
        // instantiate the destination view controller
        let greenOnion = GreenVC(arg: payload)
        // setup the receiving function before transitioning to destination
        greenOnion.reverseUpdate = reverseArgReceived
        self.navigationController?.pushViewController(greenOnion, animated: true)
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
