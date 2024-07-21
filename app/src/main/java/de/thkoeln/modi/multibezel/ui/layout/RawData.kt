package de.thkoeln.modi.multibezel.ui.layout

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.thkoeln.modi.multibezel.viewmodel.RawDataViewModel

@Composable
fun SensorSections(rawDataViewModel: RawDataViewModel = viewModel()){
    val sensorStates by rawDataViewModel.sensorStates.observeAsState(initial = emptyList())
    if(sensorStates.isNotEmpty())
        SensorSections(sectionStates = sensorStates)
}


@Composable
fun SensorSections(
    sectionStates: List<Int>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        for (i in 0 until 8) {
            val angle = (i * 45).toFloat() + 22.5
            val xOffset = 0.6f * kotlin.math.cos(Math.toRadians(angle)).toFloat()
            val yOffset = 0.6f * kotlin.math.sin(Math.toRadians(angle)).toFloat()

            Section(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(0.2f),
                state = sectionStates[i],
                xOffset = xOffset,
                yOffset = yOffset
            )
        }
    }
}

@Preview(device = "id:wearos_small_round", showBackground = true)
@Composable
fun CircularSectionsPreview() {
    val sectionStates = listOf(0, 0, 0, 0, 0, 0, 0, 0)
    SensorSections(sectionStates = sectionStates)
}

@Composable
fun Section(
    modifier: Modifier = Modifier,
    state: Int,
    xOffset: Float,
    yOffset: Float
) {
    val scale by animateFloatAsState(if (state == 1) 1.2f else 1f)
    val color by animateColorAsState(if (state == 1) Color.Green else Color.Gray)

    Box(
        modifier = modifier
            .scale(scale)
            .offset(x = (xOffset * 100).dp, y = (yOffset * 100).dp)
            .size(20.dp)
            .background(color)
    )
}

@Preview(device = "id:wearos_small_round", showBackground = true)
@Composable
fun SectionPreview() {
    Section(state = 1, xOffset = 1f, yOffset = 1f)
}