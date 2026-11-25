package com.example.androidimplementation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.androidimplementation.models.Conversation
import com.example.androidimplementation.store.ChatStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface MovieAgentUiState {
    data class Chat(
        val conversation: Conversation,
        val openedFromHistory: Boolean
    ) : MovieAgentUiState

    data class History(val conversations: List<Conversation>) : MovieAgentUiState
}

class MovieAgentViewModel(private val savedState: SavedStateHandle) : ViewModel() {
    private val store = ChatStore()
    private val mutableUiState = MutableStateFlow(restoreScreen())
    val uiState: StateFlow<MovieAgentUiState> = mutableUiState.asStateFlow()

    fun openHistory() {
        savedState[ShowingHistory] = true
        savedState.remove<Int>(SelectedId)
        mutableUiState.value = history()
    }

    fun selectConversation(id: Int) {
        if (uiState.value !is MovieAgentUiState.History) return
        val conversation = store.conversations.firstOrNull { it.id == id } ?: return

        savedState[ShowingHistory] = false
        savedState[SelectedId] = id
        mutableUiState.value = MovieAgentUiState.Chat(conversation, openedFromHistory = true)
    }

    fun goBack() {
        when (val screen = uiState.value) {
            is MovieAgentUiState.Chat -> if (screen.openedFromHistory) openHistory()
            is MovieAgentUiState.History -> {
                savedState[ShowingHistory] = false
                mutableUiState.value = initialChat()
            }
        }
    }

    fun sendMessage(text: String) {
        val chat = uiState.value as? MovieAgentUiState.Chat ?: return
        if (store.sendMessage(chat.conversation.id, text)) {
            val updated = store.conversations.first { it.id == chat.conversation.id }
            mutableUiState.value = chat.copy(conversation = updated)
        }
    }

    private fun restoreScreen(): MovieAgentUiState {
        if (savedState.get<Boolean>(ShowingHistory) == true) return history()

        val selectedId = savedState.get<Int>(SelectedId)
        val selected = store.conversations.firstOrNull { it.id == selectedId }
        return if (selected == null) initialChat()
        else MovieAgentUiState.Chat(selected, openedFromHistory = true)
    }

    private fun initialChat() =
        MovieAgentUiState.Chat(store.conversations.first(), openedFromHistory = false)

    private fun history() = MovieAgentUiState.History(
        store.conversations.sortedByDescending { it.messages.lastOrNull()?.date }
    )

    private companion object {
        const val ShowingHistory = "showingHistory"
        const val SelectedId = "selectedConversationId"
    }
}
