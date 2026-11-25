package com.example.androidimplementation.screens.chat

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.androidimplementation.models.Message
import com.example.androidimplementation.ui.theme.ScreenPadding

@Composable
fun MessageList(messages: List<Message>, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    var previousCount by remember { mutableIntStateOf(messages.size) }
    var enteringIndex by remember { mutableIntStateOf(-1) }
    val entrance = remember { Animatable(1f) }

    LaunchedEffect(messages.size) {
        val addedMessage = messages.size > previousCount
        previousCount = messages.size
        if (addedMessage) {
            entrance.snapTo(0f)
            enteringIndex = messages.lastIndex
            listState.scrollToItem(0)
            entrance.animateTo(1f, tween(durationMillis = 240))
            enteringIndex = -1
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
        reverseLayout = true,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        contentPadding = PaddingValues(horizontal = ScreenPadding, vertical = 16.dp)
    ) {
        itemsIndexed(messages.asReversed(), key = { index, _ -> messages.lastIndex - index }) { index, message ->
            val messageIndex = messages.lastIndex - index
            MessageBubble(
                message = message,
                modifier = Modifier.graphicsLayer {
                    if (enteringIndex == messageIndex) {
                        val progress = entrance.value
                        alpha = 0.35f + 0.65f * progress
                        translationY = 8.dp.toPx() * (1f - progress)
                    } else {
                        alpha = 1f
                        translationY = 0f
                    }
                }
            )
        }
    }
}
