package de.thkoeln.modi.multibezel.viewmodel

import androidx.media3.common.Player
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.media.data.repository.PlayerRepositoryImpl
import com.google.android.horologist.media.model.Media
import com.google.android.horologist.media.ui.state.PlayerViewModel

@OptIn(ExperimentalHorologistApi::class)
class MusicPlayerViewModel(private val playerRepository: PlayerRepositoryImpl = PlayerRepositoryImpl()) :
    PlayerViewModel(playerRepository) {

    fun connectRepoToPlayer(player: Player) {
        playerRepository.connect(player) {}
        setMediaTest()
    }

    private fun setMediaTest() {
        playerRepository.setMedia(
            Media(
                id = "wake_up_02",
                uri = "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/02_-_Geisha.mp3",
                title = "Geisha",
                artist = "The Kyoto Connection"
            )
        )
    }
}