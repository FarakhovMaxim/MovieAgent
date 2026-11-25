package com.example.androidimplementation.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.androidimplementation.R
import com.example.androidimplementation.models.Conversation
import com.example.androidimplementation.ui.theme.ScreenPadding
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun HistoryScreen(
    conversations: List<Conversation>,
    onBack: () -> Unit,
    onSelect: (Int) -> Unit
) {
    Scaffold(
        topBar = { HistoryHeader(onBack) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(conversations, key = { it.id }) { conversation ->
                ConversationRow(
                    conversation = conversation,
                    onClick = { onSelect(conversation.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryHeader(onBack: () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(R.string.history)) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back)
                )
            }
        }
    )
}

@Composable
private fun ConversationRow(conversation: Conversation, onClick: () -> Unit) {
    val lastMessage = conversation.messages.last()
    val date = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        .format(lastMessage.date.atZone(ZoneId.systemDefault()))

    Surface(onClick = onClick, shape = RoundedCornerShape(16.dp)) {
        ListItem(
            headlineContent = { Text(conversation.title) },
            overlineContent = { Text(date) },
            supportingContent = {
                Text(lastMessage.text, maxLines = 2, overflow = TextOverflow.Ellipsis)
            },
            colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surface)
        )
    }
}
