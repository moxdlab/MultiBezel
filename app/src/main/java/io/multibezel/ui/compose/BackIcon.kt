package io.multibezel.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.multibezel.R

@Composable
fun NavigateBack(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Box(modifier = modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "Lock Icon",
            tint = Color.White,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .clickable {
                    onClick()
                }
        )
    }
}

@Preview(device = "id:wearos_small_round", showSystemUi = true, showBackground = true)
@Composable
fun NavigateBackPreview(){
    NavigateBack {}
}