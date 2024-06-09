package de.thkoeln.modi.multibezel.ui.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import de.thkoeln.modi.multibezel.viewmodel.MusicPlayerViewModel

@Composable
fun MusicPlayer(musicPlayerViewModel: MusicPlayerViewModel = viewModel()){

}

@Preview(device = "id:wearos_large_round", showSystemUi = true)
@Composable
fun MusicPlayerPreview(){
    MusicPlayer()
}