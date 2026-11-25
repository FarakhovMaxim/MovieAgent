import UIKit

final class HistoryViewController: UITableViewController {

    // MARK: - State

    private let store: ChatStore
    private var conversations: [Conversation] = []

    // MARK: - Initialization

    init(store: ChatStore) {
        self.store = store
        super.init(style: .insetGrouped)
    }

    required init?(coder: NSCoder) { nil }

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        setupTableView()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        reloadConversations()
    }

    // MARK: - Setup

    private func setupTableView() {
        title = "История диалогов"
        tableView.backgroundColor = Theme.background
        tableView.rowHeight = UITableView.automaticDimension
        tableView.estimatedRowHeight = 88
        tableView.register(UITableViewCell.self, forCellReuseIdentifier: "conversation")
    }

    // MARK: - Conversations

    private func reloadConversations() {
        conversations = store.conversations.sorted {
            ($0.messages.last?.date ?? .distantPast) > ($1.messages.last?.date ?? .distantPast)
        }
        tableView.reloadData()
    }

    // MARK: - Table View Data Source

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        conversations.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "conversation", for: indexPath)
        let conversation = conversations[indexPath.row]
        var content = cell.defaultContentConfiguration()
        content.text = conversation.title
        content.textProperties.font = .preferredFont(forTextStyle: .headline)
        content.secondaryTextProperties.font = .preferredFont(forTextStyle: .subheadline)
        content.secondaryTextProperties.color = .secondaryLabel
        content.secondaryTextProperties.numberOfLines = 3
        if let last = conversation.messages.last {
            let date = last.date.formatted(date: .abbreviated, time: .shortened)
            let preview = String(last.text.prefix(100)) + (last.text.count > 100 ? "…" : "")
            content.secondaryText = "\(date)\n\(preview)"
        }
        cell.contentConfiguration = content
        cell.accessoryType = .disclosureIndicator
        return cell
    }

    // MARK: - Table View Delegate

    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let conversation = conversations[indexPath.row]
        navigationController?.pushViewController(
            ChatViewController(store: store, conversationID: conversation.id),
            animated: true
        )
    }
}
