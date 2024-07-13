package de.thkoeln.modi.multibezel.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import de.thkoeln.modi.multibezel.ui.compose.MusicPlayerControls
import de.thkoeln.modi.multibezel.viewmodel.CustomMusicPlayerViewModel

@Composable
fun CustomMusicPlayerScreen(customMusicPlayerViewModel: CustomMusicPlayerViewModel = viewModel()) {
    customMusicPlayerViewModel.initMediaPlayer(LocalContext.current)
    var isPlaying by remember { mutableStateOf(false) }

    CustomMusicPlayerScreen(
        onPlayPauseClick = {
            customMusicPlayerViewModel.playPause()
            isPlaying = !isPlaying },
        isPlaying = isPlaying,
        progress = 0.5f,
        onPreviousClick = { customMusicPlayerViewModel.onPreviousSong() },
        onNextClick = { customMusicPlayerViewModel.onNextSong() }
    )
}

@Composable
fun CustomMusicPlayerScreen(
    isPlaying: Boolean,
    progress: Float,
    onPlayPauseClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MusicPlayerControls(
            isPlaying = isPlaying,
            progress = progress,
            onPlayPauseClick = { onPlayPauseClick() },
            onPreviousClick = { onPreviousClick() },
            onNextClick = { onNextClick() },
        )
    }
}

@Preview(device = "id:wearos_small_round", showSystemUi = true, showBackground = true)
@Composable
fun CustomMusicPlayerPreview() {
    var isPlaying by remember { mutableStateOf(false) }
    CustomMusicPlayerScreen(
        isPlaying = isPlaying,
        onPlayPauseClick = { isPlaying = !isPlaying },
        progress = 0.4f,
        onPreviousClick = {},
        onNextClick = {}
    )
}