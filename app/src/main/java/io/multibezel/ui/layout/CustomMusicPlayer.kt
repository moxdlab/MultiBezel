package io.multibezel.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import io.multibezel.ui.compose.LockIcon
import io.multibezel.ui.compose.MusicPlayerControls
import io.multibezel.ui.compose.NavigateBack
import io.multibezel.ui.compose.VolumeBar
import io.multibezel.ui.compose.gesturesDisabled
import io.multibezel.viewmodel.CustomMusicPlayerViewModel

@Composable
fun CustomMusicPlayerScreen(
    customMusicPlayerViewModel: CustomMusicPlayerViewModel = viewModel(),
    navigateBack: () -> Unit
) {
    customMusicPlayerViewModel.initMusicPlayer(LocalContext.current, LocalHapticFeedback.current)
    val isPlaying by customMusicPlayerViewModel.isPlaying.observeAsState(false)
    val progress by customMusicPlayerViewModel.progress.observeAsState(0F)
    val volume by customMusicPlayerViewModel.volume.observeAsState(0.5F)
    val song by customMusicPlayerViewModel.currentSong.observeAsState()

    CustomMusicPlayerScreen(
        songTitle = song?.title ?: "unknown",
        artist = song?.artist ?: "unknown",
        onPlayPauseClick = {
            customMusicPlayerViewModel.playPause()
        },
        isPlaying = isPlaying ?: false,
        progress = progress,
        volume = volume,
        onPreviousClick = { customMusicPlayerViewModel.previousSong() },
        onNextClick = { customMusicPlayerViewModel.nextSong() },
        onIncreaseClick = { customMusicPlayerViewModel.increasePitch() },
        onBackClick = { navigateBack() }
    )
}

@Composable
fun CustomMusicPlayerScreen(
    isPlaying: Boolean,
    progress: Float,
    volume: Float,
    songTitle: String,
    artist: String,
    onPlayPauseClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onIncreaseClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var touchIsBlocked by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 1.dp, start = 8.dp, end = 8.dp)
                    .gesturesDisabled(touchIsBlocked),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NavigateBack(
                        modifier = Modifier
                            .size(14.dp)
                            .gesturesDisabled(touchIsBlocked)
                    ) { onBackClick() }
                    LockIcon(
                        modifier = Modifier.size(14.dp),
                        isLocked = touchIsBlocked
                    ) { touchIsBlocked = !touchIsBlocked }
                }
                Text(
                    text = songTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = artist,
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            MusicPlayerControls(
                modifier = Modifier
                    .fillMaxWidth()
                    .gesturesDisabled(touchIsBlocked),
                isPlaying = isPlaying,
                progress = progress,
                onPlayPauseClick = onPlayPauseClick,
                onPreviousClick = onPreviousClick,
                onNextClick = onNextClick,
                onIncreaseClick = onIncreaseClick
            )
            VolumeBar(
                modifier = Modifier
                    .gesturesDisabled(touchIsBlocked)
                    .padding(horizontal = 10.dp),
                volume = volume
            ) {}
        }
    }
}

@Preview(device = "id:wearos_small_round", showSystemUi = true, showBackground = true)
@Composable
fun CustomMusicPlayerPreview() {
    var isPlaying by remember { mutableStateOf(false) }
    CustomMusicPlayerScreen(
        songTitle = "Africa",
        artist = "Toto",
        isPlaying = isPlaying,
        onPlayPauseClick = { isPlaying = !isPlaying },
        progress = 0.4f,
        volume = 0.6f,
        onPreviousClick = {},
        onNextClick = {},
        onIncreaseClick = {},
        onBackClick = {}
    )
}