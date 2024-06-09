package de.thkoeln.modi.multibezel.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text

@Composable
fun Welcome(
    navigateMusicPlayer: () -> Unit = {},
    navigateRawData: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = { navigateMusicPlayer() },
            shape = RectangleShape,
            modifier = Modifier
                .padding(16.dp)
                .size(width = 120.dp, height = 40.dp)
                .fillMaxWidth()
        ) {
            Text("Music Player")
        }
        Button(
            onClick = { navigateRawData() },
            shape = RectangleShape,
            modifier = Modifier
                .padding(16.dp)
                .size(width = 120.dp, height = 40.dp)
                .fillMaxWidth()
        ) {
            Text("Raw Data")
        }
    }
}

@Preview(device = "id:wearos_large_round", showSystemUi = true)
@Composable
fun WelcomePreview() {
    Welcome()
}