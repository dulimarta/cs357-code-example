//
//  MainVC.swift
//  ios-tableview
//
//  Created by Hans Dulimarta on 3/25/24.
//

import UIKit

class MainVC: UIViewController {
    lazy var tabAdapter = MyTableAdapter()
    private var myTopTable: UITableView!
    private var myBottomTable: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = .cyan

        self.myTopTable = self.view.viewWithTag(20) as? UITableView
        self.myTopTable.register(UINib(nibName: "MyFakerCell", bundle: nil), forCellReuseIdentifier: "fakecell")
        self.myBottomTable = self.view.viewWithTag(30) as? UITableView
        self.myBottomTable.register(UINib(nibName: "MyTableCell", bundle: nil), forCellReuseIdentifier: "blueberry")
//        self.myTable.register(UITableViewCell.self, forCellReuseIdentifier: "blueberry")
        self.myBottomTable.dataSource = self
        self.myTopTable.dataSource = tabAdapter
        // Do any additional setup after loading the view.
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

extension MainVC: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 37
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "blueberry", for: indexPath)
        let ownCell = cell as? MyTableCell
        ownCell?.populateRow(row: indexPath.row)
//        cell.backgroundColor = indexPath.row % 2 == 0 ? .white : .lightGray
//        var config = cell.defaultContentConfiguration()
//        config.secondaryText = "I'm at row \(indexPath.row + 1)"
//        cell.contentConfiguration = config
//        cell.textLabel?.text = "Row \(indexPath.row + 1)"
//        cell.detailTextLabel?.text = "Details of row \(indexPath.row + 1)"
//        cell.imageView?.image = UIImage(systemName: "hand.thumbsup")
        return cell
    }
    
    
}
