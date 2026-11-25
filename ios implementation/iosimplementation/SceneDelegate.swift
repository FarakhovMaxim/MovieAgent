import UIKit

final class SceneDelegate: UIResponder, UIWindowSceneDelegate {
    var window: UIWindow?

    func scene(
        _ scene: UIScene,
        willConnectTo session: UISceneSession,
        options connectionOptions: UIScene.ConnectionOptions
    ) {
        guard let windowScene = scene as? UIWindowScene else { return }

        let window = UIWindow(windowScene: windowScene)
        let store = ChatStore()
        guard let first = store.conversations.first else { return }
        let chat = ChatViewController(store: store, conversationID: first.id)
        let navigation = UINavigationController(rootViewController: chat)
        navigation.view.tintColor = Theme.tint
        window.rootViewController = navigation
        window.makeKeyAndVisible()
        self.window = window
    }
}
