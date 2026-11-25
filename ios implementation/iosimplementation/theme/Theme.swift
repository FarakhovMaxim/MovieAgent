import UIKit

enum Theme {
    static let tint = UIColor.systemIndigo
    static let background = UIColor.systemGroupedBackground
    static let surface = UIColor.secondarySystemGroupedBackground
    static let spacing: CGFloat = 16

    static func label(_ text: String, style: UIFont.TextStyle, color: UIColor = .label) -> UILabel {
        let label = UILabel()
        label.text = text
        label.font = .preferredFont(forTextStyle: style)
        label.adjustsFontForContentSizeCategory = true
        label.textColor = color
        label.numberOfLines = 0
        return label
    }
}
