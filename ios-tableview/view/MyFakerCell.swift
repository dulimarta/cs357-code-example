//
//  MyFakerCell.swift
//  ios-tableview
//
//  Created by Hans Dulimarta on 3/25/24.
//

import UIKit
import Fakery

class MyFakerCell: UITableViewCell {
    private var dataFaker = Faker()

    var name: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        name = contentView.viewWithTag(11) as? UILabel
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func populate() {
        name.text = dataFaker.name.firstName() + " " + dataFaker.name.lastName()
    }
}
