package com.example.androidimplementation.models

import java.time.Instant

enum class Author { User, Assistant }

data class Film(val title: String, val details: String)

data class MovieCard(
    val title: String,
    val description: String,
    val films: List<Film>
)

data class Message(
    val author: Author,
    val text: String,
    val date: Instant,
    val card: MovieCard? = null
)

data class Conversation(
    val id: Int,
    val title: String,
    val messages: List<Message>
)
