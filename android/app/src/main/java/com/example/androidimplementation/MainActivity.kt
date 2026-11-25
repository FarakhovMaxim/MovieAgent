package com.example.androidimplementation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.androidimplementation.viewmodel.MovieAgentViewModel
import com.example.androidimplementation.ui.theme.AndroidImplementationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val model = ViewModelProvider(this)[MovieAgentViewModel::class.java]
        setContent {
            AndroidImplementationTheme {
                MovieAgentApp(model)
            }
        }
    }
}
