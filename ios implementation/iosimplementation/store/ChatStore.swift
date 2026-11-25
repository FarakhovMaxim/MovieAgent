import Foundation

final class ChatStore {
    private(set) var conversations: [Conversation] = {
        let now = Date()
        let picks = MovieCard(
            title: "Космос на вечер",
            description: "Три фильма с разным настроением.",
            films: [
                .init(title: "Прибытие", details: "2016 · Первый контакт и загадка языка"),
                .init(title: "Марсианин", details: "2015 · Выживание и юмор"),
                .init(title: "Интерстеллар", details: "2014 · Путешествие и семейная драма")
            ]
        )

        func dialog(_ title: String, _ prompt: String, _ answer: String, daysAgo: Int, card: MovieCard? = nil) -> Conversation {
            let date = now.addingTimeInterval(-Double(daysAgo) * 86_400)
            return Conversation(title: title, messages: [
                Message(author: .user, text: prompt, date: date.addingTimeInterval(-60)),
                Message(author: .assistant, text: answer, date: date, card: card)
            ])
        }

        return [
            dialog("Вечер фантастики", "Что посмотреть про космос?",
                   "Собрал три варианта. Начни с «Прибытия», если хочется истории, которую интересно обсуждать после титров.",
                   daysAgo: 0, card: picks),
            dialog("Уютная комедия", "Хочу лёгкую комедию на вечер.",
                   "Попробуй «Стажёра» (2015). Это спокойная комедия про дружбу и работу с Робертом Де Ниро и Энн Хэтэуэй.",
                   daysAgo: 1),
            dialog("Детектив с загадкой", "Посоветуй детектив, где можно угадать развязку.",
                   "«Достать ножи» (2019) подойдёт: у каждого героя есть мотив, а детали разбросаны по всему фильму.",
                   daysAgo: 2)
        ]
    }()

    func messages(in id: UUID) -> [Message] {
        conversations.first { $0.id == id }?.messages ?? []
    }

    @discardableResult
    func send(_ input: String, in id: UUID) -> Bool {
        let text = input.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !text.isEmpty, let index = conversations.firstIndex(where: { $0.id == id }) else {
            return false
        }
        conversations[index].messages.append(Message(author: .user, text: text, date: Date()))
        return true
    }
}
