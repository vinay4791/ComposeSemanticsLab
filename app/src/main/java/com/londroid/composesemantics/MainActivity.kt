package com.londroid.composesemantics

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.londroid.composesemantics.navigation.ComposeSemanticsNavHost
import com.londroid.composesemantics.ui.theme.ComposeSemanticsLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeSemanticsLabTheme {
                ComposeSemanticsNavHost()
            }
        }
    }
}
