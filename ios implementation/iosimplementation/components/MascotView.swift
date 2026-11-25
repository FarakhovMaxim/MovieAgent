import UIKit

final class MascotView: UIView {
    init(size: CGFloat) {
        super.init(frame: .zero)
        backgroundColor = .clear
        isOpaque = false
        isUserInteractionEnabled = false
        isAccessibilityElement = false
        NSLayoutConstraint.activate([
            widthAnchor.constraint(equalToConstant: size),
            heightAnchor.constraint(equalToConstant: size)
        ])
    }

    required init?(coder: NSCoder) { nil }

    override func draw(_ rect: CGRect) {
        guard let context = UIGraphicsGetCurrentContext() else { return }
        let scale = min(bounds.width, bounds.height) / 40
        context.saveGState()
        context.translateBy(x: (bounds.width - 40 * scale) / 2, y: (bounds.height - 40 * scale) / 2)
        context.scaleBy(x: scale, y: scale)
        defer { context.restoreGState() }

        let indigo = UIColor(red: 0.20, green: 0.16, blue: 0.43, alpha: 1)
        let lavender = UIColor(red: 0.79, green: 0.73, blue: 1, alpha: 1)
        let gold = UIColor(red: 1, green: 0.78, blue: 0.32, alpha: 1)

        indigo.setFill()
        UIBezierPath(ovalIn: CGRect(x: 0, y: 0, width: 40, height: 40)).fill()

        gold.setFill()
        let lens = UIBezierPath()
        lens.move(to: CGPoint(x: 24, y: 20))
        lens.addLine(to: CGPoint(x: 34, y: 16))
        lens.addLine(to: CGPoint(x: 34, y: 29))
        lens.addLine(to: CGPoint(x: 24, y: 25))
        lens.close()
        lens.fill()

        lavender.setFill()
        UIBezierPath(ovalIn: CGRect(x: 8, y: 8, width: 10, height: 10)).fill()
        UIBezierPath(ovalIn: CGRect(x: 18, y: 6, width: 12, height: 12)).fill()
        UIBezierPath(roundedRect: CGRect(x: 7, y: 17, width: 22, height: 15), cornerRadius: 5).fill()

        indigo.setFill()
        UIBezierPath(ovalIn: CGRect(x: 11.5, y: 11.5, width: 3, height: 3)).fill()
        UIBezierPath(ovalIn: CGRect(x: 22, y: 10, width: 4, height: 4)).fill()
        UIBezierPath(ovalIn: CGRect(x: 11, y: 21, width: 3, height: 3)).fill()
        UIBezierPath(ovalIn: CGRect(x: 21, y: 21, width: 3, height: 3)).fill()

        indigo.setStroke()
        let smile = UIBezierPath()
        smile.move(to: CGPoint(x: 15, y: 26))
        smile.addQuadCurve(to: CGPoint(x: 20, y: 26), controlPoint: CGPoint(x: 17.5, y: 30))
        smile.lineWidth = 1.5
        smile.lineCapStyle = .round
        smile.stroke()
    }
}
