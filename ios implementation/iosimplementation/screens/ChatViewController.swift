import UIKit

final class ChatViewController: UIViewController {

    // MARK: - UI Elements

    private let header: UIStackView = {
        let stack = UIStackView(arrangedSubviews: [
            MascotView(size: 34),
            Theme.label("MovieAgent", style: .headline)
        ])
        stack.alignment = .center
        stack.spacing = 7
        return stack
    }()

    private let historyButton: UIBarButtonItem = {
        let button = UIBarButtonItem(image: UIImage(systemName: "clock"), style: .plain, target: nil, action: nil)
        button.accessibilityLabel = "История диалогов"
        return button
    }()

    private let scrollView: UIScrollView = {
        let scrollView = UIScrollView()
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        scrollView.keyboardDismissMode = .interactive
        return scrollView
    }()

    private let messages: UIStackView = {
        let stack = UIStackView()
        stack.translatesAutoresizingMaskIntoConstraints = false
        stack.axis = .vertical
        stack.spacing = 12
        return stack
    }()

    private let input: UITextField = {
        let field = UITextField()
        field.placeholder = "Сообщение"
        field.borderStyle = .roundedRect
        field.font = .preferredFont(forTextStyle: .body)
        field.adjustsFontForContentSizeCategory = true
        field.returnKeyType = .send
        return field
    }()

    private let sendButton: UIButton = {
        var configuration = UIButton.Configuration.filled()
        configuration.image = UIImage(systemName: "arrow.up")
        configuration.cornerStyle = .capsule

        let button = UIButton(type: .system)
        button.configuration = configuration
        button.accessibilityLabel = "Отправить"
        button.isEnabled = false
        return button
    }()

    private let composer: UIStackView = {
        let stack = UIStackView()
        stack.translatesAutoresizingMaskIntoConstraints = false
        stack.alignment = .center
        stack.spacing = 8
        stack.layoutMargins = UIEdgeInsets(top: 10, left: Theme.spacing, bottom: 10, right: Theme.spacing)
        stack.isLayoutMarginsRelativeArrangement = true
        stack.backgroundColor = Theme.surface
        return stack
    }()

    // MARK: - State

    private let store: ChatStore
    private let conversationID: UUID
    private var scrollOnLayout = true

    // MARK: - Initialization

    init(store: ChatStore, conversationID: UUID) {
        self.store = store
        self.conversationID = conversationID
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) { nil }

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
        setupConstraints()
        setupActions()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        refreshMessages()
    }

    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        if scrollOnLayout {
            scrollOnLayout = false
            scrollToLatest(animated: false)
        }
    }

    // MARK: - Setup

    private func setupUI() {
        view.backgroundColor = Theme.background
        title = "MovieAgent"
        navigationItem.titleView = header
        navigationItem.rightBarButtonItem = historyButton

        scrollView.addSubview(messages)
        composer.addArrangedSubview(input)
        composer.addArrangedSubview(sendButton)
        view.addSubview(scrollView)
        view.addSubview(composer)
    }

    private func setupConstraints() {
        let safe = view.safeAreaLayoutGuide
        let content = scrollView.contentLayoutGuide
        NSLayoutConstraint.activate([
            scrollView.topAnchor.constraint(equalTo: safe.topAnchor),
            scrollView.leadingAnchor.constraint(equalTo: safe.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: safe.trailingAnchor),
            scrollView.bottomAnchor.constraint(equalTo: composer.topAnchor),
            messages.topAnchor.constraint(equalTo: content.topAnchor, constant: Theme.spacing),
            messages.bottomAnchor.constraint(equalTo: content.bottomAnchor, constant: -Theme.spacing),
            messages.leadingAnchor.constraint(equalTo: content.leadingAnchor, constant: Theme.spacing),
            messages.trailingAnchor.constraint(equalTo: content.trailingAnchor, constant: -Theme.spacing),
            messages.widthAnchor.constraint(equalTo: scrollView.frameLayoutGuide.widthAnchor, constant: -2 * Theme.spacing),
            composer.leadingAnchor.constraint(equalTo: safe.leadingAnchor),
            composer.trailingAnchor.constraint(equalTo: safe.trailingAnchor),
            composer.bottomAnchor.constraint(equalTo: view.keyboardLayoutGuide.topAnchor),
            input.heightAnchor.constraint(greaterThanOrEqualToConstant: 44),
            sendButton.widthAnchor.constraint(equalToConstant: 44),
            sendButton.heightAnchor.constraint(equalToConstant: 44)
        ])
    }

    private func setupActions() {
        historyButton.target = self
        historyButton.action = #selector(openHistory)
        input.delegate = self
        input.addTarget(self, action: #selector(updateSendButton), for: .editingChanged)
        sendButton.addTarget(self, action: #selector(send), for: .touchUpInside)
    }

    // MARK: - Messages

    private func refreshMessages() {
        let current = store.messages(in: conversationID)
        let displayedCount = messages.arrangedSubviews.count
        guard current.count > displayedCount else { return }
        current.dropFirst(displayedCount).forEach {
            messages.addArrangedSubview(MessageBubble(message: $0))
        }
        scrollOnLayout = true
    }

    @objc private func updateSendButton() {
        sendButton.isEnabled = !(input.text ?? "").trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
    }

    @objc private func send() {
        guard store.send(input.text ?? "", in: conversationID),
              let message = store.messages(in: conversationID).last else { return }
        input.text = ""
        updateSendButton()
        showSentMessage(message)
    }

    // MARK: - Message Animation

    private func showSentMessage(_ message: Message) {
        let bubble = MessageBubble(message: message)
        let shouldAnimate = !UIAccessibility.isReduceMotionEnabled
        if shouldAnimate {
            bubble.alpha = 0.35
            bubble.transform = CGAffineTransform(translationX: 0, y: 8)
        }
        messages.addArrangedSubview(bubble)
        view.layoutIfNeeded()
        scrollToLatest(animated: shouldAnimate)

        if shouldAnimate {
            UIView.animate(
                withDuration: 0.24,
                delay: 0,
                options: [.curveEaseOut, .allowUserInteraction]
            ) {
                bubble.alpha = 1
                bubble.transform = .identity
            }
        }
    }

    private func scrollToLatest(animated: Bool) {
        let insets = scrollView.adjustedContentInset
        let offset = max(-insets.top, scrollView.contentSize.height - scrollView.bounds.height + insets.bottom)
        scrollView.setContentOffset(CGPoint(x: 0, y: offset), animated: animated)
    }

    // MARK: - Navigation

    @objc private func openHistory() {
        guard let navigationController else { return }
        if let history = navigationController.viewControllers.last(where: { $0 is HistoryViewController }) {
            navigationController.popToViewController(history, animated: true)
        } else {
            navigationController.pushViewController(HistoryViewController(store: store), animated: true)
        }
    }
}

// MARK: - UITextFieldDelegate

extension ChatViewController: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        send()
        return false
    }

    func textFieldDidBeginEditing(_ textField: UITextField) {
        scrollOnLayout = true
    }
}
