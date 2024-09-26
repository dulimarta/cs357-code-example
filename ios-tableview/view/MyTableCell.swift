//
//  MyTableCell.swift
//  ios-tableview
//
//  Created by Hans Dulimarta on 3/25/24.
//

import UIKit

class MyTableCell: UITableViewCell {

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
        
    func populateRow(row: Int) {
        // iOS 15 or later
        var config = self.defaultContentConfiguration()
        config.text = "Row \(row+1)"
        config.secondaryText = "Details of \(row+1)"
        config.image = UIImage(systemName: "hand.thumbsup")
        self.contentConfiguration = config
        
        // iOS 14 or older
        //self.textLabel?.text = "Row \(row + 1)"
        //self.detailTextLabel?.text = "Details of row \(row + 1)"
        //self.imageView?.image = UIImage(systemName: "hand.thumbsup")
    }
}
