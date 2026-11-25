package com.example.androidimplementation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import com.example.androidimplementation.screens.chat.ChatScreen
import com.example.androidimplementation.screens.history.HistoryScreen
import com.example.androidimplementation.viewmodel.MovieAgentUiState
import com.example.androidimplementation.viewmodel.MovieAgentViewModel

@Composable
fun MovieAgentApp(viewModel: MovieAgentViewModel) {
    val state by viewModel.uiState.collectAsState()

    MovieAgentContent(
        state = state,
        onOpenHistory = viewModel::openHistory,
        onSelectConversation = viewModel::selectConversation,
        onBack = viewModel::goBack,
        onSendMessage = viewModel::sendMessage
    )
}

@Composable
private fun MovieAgentContent(
    state: MovieAgentUiState,
    onOpenHistory: () -> Unit,
    onSelectConversation: (Int) -> Unit,
    onBack: () -> Unit,
    onSendMessage: (String) -> Unit
) {
    val canGoBack = when (state) {
        is MovieAgentUiState.History -> true
        is MovieAgentUiState.Chat -> state.openedFromHistory
    }
    BackHandler(enabled = canGoBack, onBack = onBack)

    when (state) {
        is MovieAgentUiState.History -> HistoryScreen(
            conversations = state.conversations,
            onBack = onBack,
            onSelect = onSelectConversation
        )
        is MovieAgentUiState.Chat -> key(state.conversation.id) {
            ChatScreen(
                conversation = state.conversation,
                onOpenHistory = onOpenHistory,
                onSendMessage = onSendMessage
            )
        }
    }
}
