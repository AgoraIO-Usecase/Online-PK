//
//  UserCell.swift
//  Agora-Online-PK
//
//  Created by ZhangJi on 2018/6/4.
//  Copyright © 2018 CavanSu. All rights reserved.
//

import UIKit

class UserCell: UITableViewCell {

    @IBOutlet weak var userProfileImageView: UIImageView!
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var watcherCountLabel: UILabel!
    @IBOutlet weak var backgroundImageView: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    func fakeTheUesrWith(_ num: Int) {
        self.userNameLabel.adjustsFontSizeToFitWidth = true
        let random = Int(num % userProfileLists.count)
        self.userProfileImageView.image = userProfileLists[random]
        self.backgroundImageView.image = backgroundLists[random]
        self.userNameLabel.text = userNameList[random]
        self.watcherCountLabel.text = String(Int(arc4random() % 20000))
    }
}
