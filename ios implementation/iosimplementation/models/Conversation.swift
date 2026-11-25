import Foundation

struct MovieCard {
    struct Film {
        let title: String
        let details: String
    }

    let title: String
    let description: String
    let films: [Film]
}

struct Message {
    enum Author {
        case user
        case assistant
    }

    let author: Author
    let text: String
    let date: Date
    var card: MovieCard? = nil
}

struct Conversation {
    let id = UUID()
    let title: String
    var messages: [Message]
}
