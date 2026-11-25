import UIKit

final class MessageBubble: UIView {
    init(message: Message) {
        super.init(frame: .zero)

        let isUser = message.author == .user
        let label = Theme.label(message.text, style: .body, color: isUser ? .white : .label)
        let bubble = UIStackView(arrangedSubviews: [label])
        bubble.axis = .vertical
        bubble.spacing = 12
        bubble.layoutMargins = UIEdgeInsets(top: 12, left: 14, bottom: 12, right: 14)
        bubble.isLayoutMarginsRelativeArrangement = true
        bubble.backgroundColor = isUser ? Theme.tint : Theme.surface
        bubble.layer.cornerRadius = 16
        if let card = message.card {
            bubble.addArrangedSubview(Self.makeCard(card))
        }

        addSubview(bubble)
        bubble.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            bubble.topAnchor.constraint(equalTo: topAnchor),
            bubble.bottomAnchor.constraint(equalTo: bottomAnchor),
            bubble.widthAnchor.constraint(lessThanOrEqualTo: widthAnchor, multiplier: 0.87)
        ])
        if isUser {
            bubble.trailingAnchor.constraint(equalTo: trailingAnchor).isActive = true
        } else {
            let mascot = MascotView(size: 30)
            addSubview(mascot)
            mascot.translatesAutoresizingMaskIntoConstraints = false
            NSLayoutConstraint.activate([
                mascot.leadingAnchor.constraint(equalTo: leadingAnchor),
                mascot.topAnchor.constraint(equalTo: topAnchor),
                mascot.bottomAnchor.constraint(lessThanOrEqualTo: bottomAnchor),
                bubble.leadingAnchor.constraint(equalTo: mascot.trailingAnchor, constant: 8),
                bubble.trailingAnchor.constraint(lessThanOrEqualTo: trailingAnchor)
            ])
        }
        accessibilityElements = bubble.arrangedSubviews
        label.accessibilityLabel = "\(isUser ? "Вы" : "MovieAgent"): \(message.text)"
    }

    private static func makeCard(_ card: MovieCard) -> UIStackView {
        let views: [UIView] = [
            Theme.label(card.title, style: .headline),
            Theme.label(card.description, style: .subheadline, color: .secondaryLabel)
        ] + card.films.map { film in
            let row = UIStackView(arrangedSubviews: [
                Theme.label(film.title, style: .subheadline),
                Theme.label(film.details, style: .footnote, color: .secondaryLabel)
            ])
            row.axis = .vertical
            row.spacing = 2
            return row
        }
        let stack = UIStackView(arrangedSubviews: views)
        stack.axis = .vertical
        stack.spacing = 9
        stack.layoutMargins = UIEdgeInsets(top: 12, left: 12, bottom: 12, right: 12)
        stack.isLayoutMarginsRelativeArrangement = true
        stack.backgroundColor = Theme.background
        stack.layer.cornerRadius = 12
        return stack
    }

    required init?(coder: NSCoder) { nil }
}
