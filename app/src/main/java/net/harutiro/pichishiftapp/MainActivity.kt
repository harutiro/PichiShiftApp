package net.harutiro.pichishiftapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.harutiro.pichishiftapp.ui.theme.PichiShiftAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PichiShiftAppTheme {
                PlayerScreen()
            }
        }
    }
}