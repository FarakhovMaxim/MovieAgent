package com.example.androidimplementation.screens.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidimplementation.components.Mascot
import com.example.androidimplementation.models.Author
import com.example.androidimplementation.models.Message
import com.example.androidimplementation.models.MovieCard

@Composable
fun MessageBubble(message: Message, modifier: Modifier = Modifier) {
    val isUser = message.author == Author.User
    val colors = MaterialTheme.colorScheme

    BoxWithConstraints(modifier.fillMaxWidth()) {
        val bubbleWidth = if (isUser) maxWidth * 0.87f else maxWidth - 38.dp
        Row(
            modifier = Modifier.align(if (isUser) Alignment.CenterEnd else Alignment.CenterStart),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!isUser) {
                Mascot(Modifier.size(30.dp))
            }
            Surface(
                modifier = Modifier.widthIn(max = bubbleWidth),
                shape = RoundedCornerShape(16.dp),
                color = if (isUser) colors.primary else colors.surface,
                contentColor = if (isUser) colors.onPrimary else colors.onSurface
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(message.text, style = MaterialTheme.typography.bodyLarge)
                    message.card?.let { MovieCardContent(it) }
                }
            }
        }
    }
}

@Composable
private fun MovieCardContent(card: MovieCard) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(card.title, style = MaterialTheme.typography.titleMedium)
            Text(card.description, style = MaterialTheme.typography.bodyMedium)
            card.films.forEach { film ->
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(film.title, style = MaterialTheme.typography.labelLarge)
                    Text(film.details, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
