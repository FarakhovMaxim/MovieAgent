package com.example.androidimplementation.store

import com.example.androidimplementation.models.Author
import com.example.androidimplementation.models.Conversation
import com.example.androidimplementation.models.Film
import com.example.androidimplementation.models.Message
import com.example.androidimplementation.models.MovieCard
import java.time.Instant
import java.time.temporal.ChronoUnit

class ChatStore {
    var conversations: List<Conversation> = sampleConversations()
        private set

    fun sendMessage(conversationId: Int, input: String): Boolean {
        val text = input.trim()
        if (text.isEmpty() || conversations.none { it.id == conversationId }) return false

        conversations = conversations.map { conversation ->
            if (conversation.id == conversationId) {
                val message = Message(Author.User, text, Instant.now())
                conversation.copy(messages = conversation.messages + message)
            } else {
                conversation
            }
        }
        return true
    }
}

private fun sampleConversations(): List<Conversation> {
    val now = Instant.now()
    val picks = MovieCard(
        title = "Космос на вечер",
        description = "Три фильма с разным настроением.",
        films = listOf(
            Film("Прибытие", "2016 · Первый контакт и загадка языка"),
            Film("Марсианин", "2015 · Выживание и юмор"),
            Film("Интерстеллар", "2014 · Путешествие и семейная драма")
        )
    )

    fun dialog(
        id: Int,
        title: String,
        prompt: String,
        answer: String,
        daysAgo: Long,
        card: MovieCard? = null
    ): Conversation {
        val date = now.minus(daysAgo, ChronoUnit.DAYS)
        return Conversation(
            id = id,
            title = title,
            messages = listOf(
                Message(Author.User, prompt, date.minusSeconds(60)),
                Message(Author.Assistant, answer, date, card)
            )
        )
    }

    return listOf(
        dialog(
            0,
            "Вечер фантастики",
            "Что посмотреть про космос?",
            "Собрал три варианта. Начни с «Прибытия», если хочется истории, которую интересно обсуждать после титров.",
            0,
            picks
        ),
        dialog(
            1,
            "Уютная комедия",
            "Хочу лёгкую комедию на вечер.",
            "Попробуй «Стажёра» (2015). Это спокойная комедия про дружбу и работу с Робертом Де Ниро и Энн Хэтэуэй.",
            1
        ),
        dialog(
            2,
            "Детектив с загадкой",
            "Посоветуй детектив, где можно угадать развязку.",
            "«Достать ножи» (2019) подойдёт: у каждого героя есть мотив, а детали разбросаны по всему фильму.",
            2
        )
    )
}
