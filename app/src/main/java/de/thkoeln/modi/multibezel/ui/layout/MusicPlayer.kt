package de.thkoeln.modi.multibezel.ui.layout

import android.annotation.SuppressLint
import android.os.Vibrator
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.audio.SystemAudioRepository
import com.google.android.horologist.audio.ui.VolumeViewModel
import com.google.android.horologist.media.ui.components.PodcastControlButtons
import com.google.android.horologist.media.ui.screens.player.DefaultMediaInfoDisplay
import com.google.android.horologist.media.ui.screens.player.PlayerScreen
import com.google.android.horologist.media.ui.state.PlayerUiController
import com.google.android.horologist.media.ui.state.PlayerUiState
import de.thkoeln.modi.multibezel.viewmodel.MusicPlayerViewModel

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun MusicPlayer(musicPlayerViewModel: MusicPlayerViewModel = viewModel()) {
    musicPlayerViewModel.connectRepoToPlayer(
        ExoPlayer.Builder(LocalContext.current).setSeekForwardIncrementMs(5000L)
            .setSeekBackIncrementMs(5000L)
            .build()
    )

    MusicPlayer()
}

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun MusicPlayer() {
    // TODO: Implement custom player -> so that we can handle everything by ourselves
    val viewModel = MusicPlayerViewModel()
    @SuppressLint("UnsafeOptInUsageError")
    val player = ExoPlayer.Builder(LocalContext.current)
        .setSeekForwardIncrementMs(5000L)
        .setSeekBackIncrementMs(5000L)
        .build()
    viewModel.connectRepoToPlayer(player)
    val audioRepository = SystemAudioRepository.fromContext(LocalContext.current)
    val vibrator: Vibrator = LocalContext.current.getSystemService(Vibrator::class.java)
    val volumeViewModel =  VolumeViewModel(audioRepository, audioRepository, onCleared = {
        audioRepository.close()
    }, vibrator)

    PlayerScreen(
        playerViewModel = viewModel,
        volumeViewModel = volumeViewModel,
        mediaDisplay = { playerUiState: PlayerUiState ->
            DefaultMediaInfoDisplay(playerUiState)
        },
        controlButtons = { playerUIController: PlayerUiController,
                           playerUiState: PlayerUiState ->
            PodcastControlButtons(
                playerController = playerUIController,
                playerUiState = playerUiState
            )
        },
        buttons = { }
    )
}

@Preview(device = "id:wearos_large_round", showSystemUi = true)
@Composable
fun MusicPlayerPreview() {
    MusicPlayer()
}