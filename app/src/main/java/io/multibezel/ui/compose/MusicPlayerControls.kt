package io.multibezel.ui.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import io.multibezel.R

@Composable
fun MusicControlCircularProgressBar(
    isPlaying: Boolean,
    progress: Float,
    onPlayPauseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(120.dp, 60.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val arcRadius = size.minDimension / 2 - 5f
            drawCircle(
                color = Color.LightGray,
                radius = arcRadius,
                style = Stroke(width = 10f, cap = StrokeCap.Round),
                center = Offset(size.width / 2, size.height / 2)
            )

            drawArc(
                color = Color.Green,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(width = 10f, cap = StrokeCap.Round),
                size = Size(arcRadius * 2, arcRadius * 2),
                topLeft = Offset(
                    (size.width - arcRadius * 2) / 2,
                    (size.height - arcRadius * 2) / 2
                )
            )
        }

        IconButton(onClick = onPlayPauseClick) {
            Icon(
                painter = painterResource(id = if (isPlaying) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24),
                contentDescription = if (isPlaying) "Pause" else "Play",
                tint = Color.White,
                modifier = Modifier.size(80.dp)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = "id:wearos_small_round")
@Composable
fun MusicControlCircularProgressBarPreview() {
    MusicControlCircularProgressBar(
        isPlaying = true,
        progress = 0.7f,
        onPlayPauseClick = {}
    )
}

@Composable
fun VolumeBar(
    modifier: Modifier = Modifier,
    volume: Float,
    onVolumeChange: (Float) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_volume_up_24),
            contentDescription = "Volume",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(3.dp))
        Slider(
            value = volume,
            onValueChange = onVolumeChange,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.Gray
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VolumeBarPreview() {
    VolumeBar(volume = 0.5f) {}
}

@Composable
fun MusicPlayerControls(
    isPlaying: Boolean,
    progress: Float,
    onPlayPauseClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onIncreaseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousClick) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_fast_rewind_24),
                contentDescription = "Previous Song",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }

        MusicControlCircularProgressBar(
            isPlaying = isPlaying,
            progress = progress,
            onPlayPauseClick = onPlayPauseClick,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onNextClick) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_fast_forward_24),
                contentDescription = "Next Song",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Preview(device = "id:pixel_5", showSystemUi = false, showBackground = true)
@Composable
fun MusicPlayerControlsPreview() {
    MusicPlayerControls(
        isPlaying = true,
        progress = 0.7f,
        onPlayPauseClick = {},
        onPreviousClick = {},
        onNextClick = {},
        onIncreaseClick = {}
    )
}