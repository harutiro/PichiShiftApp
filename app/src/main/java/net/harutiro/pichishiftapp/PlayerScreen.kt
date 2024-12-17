package net.harutiro.pichishiftapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = viewModel()
) {

    val context = LocalContext.current
    viewModel.init(context)

    Scaffold(
        topBar = { TopAppBar(title = { Text("Pitch Shift Player") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("再生速度: ${String.format("%.2f", viewModel.playbackSpeed.value)}", fontSize = 18.sp)
            Slider(
                value = viewModel.playbackSpeed.floatValue,
                onValueChange = {
                    viewModel.playbackSpeed.floatValue = it
                    viewModel.setPlaybackSpeed(it)
                },
                valueRange = 0.5f..2.0f,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text("音の高さ (ピッチ): ${String.format("%.2f", viewModel.pitch.value)}", fontSize = 18.sp)
            Slider(
                value = viewModel.pitch.value,
                onValueChange = {
                    viewModel.pitch.value = it
                    viewModel.setPitch(it)
                },
                valueRange = 0.5f..2.0f,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(
                    onClick = { if (!(viewModel.isPlaying.value)) viewModel.playMusic() },
                    enabled = !(viewModel.isPlaying.value)
                ) {
                    Text("再生")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { viewModel.stopMusic() },
                    enabled = viewModel.isPlaying.value
                ) {
                    Text("停止")
                }
            }
        }
    }
}