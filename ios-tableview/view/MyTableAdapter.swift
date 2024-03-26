//
//  MyTableAdapter.swift
//  ios-tableview
//
//  Created by Hans Dulimarta on 3/25/24.
//

import Foundation
import UIKit

class MyTableAdapter: NSObject, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 19
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "fakecell", for: indexPath)
        let fCell = cell as? MyFakerCell
        fCell?.populate()
        cell.backgroundColor = indexPath.row % 2 == 0 ? .yellow : .green
        return cell
    }
    
    
}
