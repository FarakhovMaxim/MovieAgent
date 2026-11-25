package com.example.androidimplementation.screens.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.androidimplementation.R
import com.example.androidimplementation.ui.theme.ScreenPadding

@Composable
fun MessageInput(onSend: (String) -> Unit) {
    var draft by rememberSaveable { mutableStateOf("") }

    fun send() {
        if (draft.isBlank()) return
        onSend(draft)
        draft = ""
    }

    Surface(color = MaterialTheme.colorScheme.surface) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ScreenPadding, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = draft,
                onValueChange = { draft = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(stringResource(R.string.message_placeholder)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = { send() })
            )
            FilledIconButton(onClick = { send() }, enabled = draft.isNotBlank()) {
                Icon(
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = stringResource(R.string.send)
                )
            }
        }
    }
}
