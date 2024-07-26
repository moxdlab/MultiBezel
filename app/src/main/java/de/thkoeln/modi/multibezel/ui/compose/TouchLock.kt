package de.thkoeln.modi.multibezel.ui.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.thkoeln.modi.multibezel.R

@Composable
fun LockIcon(
    modifier: Modifier = Modifier,
    isLocked: Boolean,
    onClick: () -> Unit
) {
    val iconColor by animateColorAsState(if (isLocked) Color.Red else Color.Green)

    Box(modifier = modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(id = if (!isLocked) R.drawable.baseline_lock_open_24 else R.drawable.baseline_lock_outline_24),
            contentDescription = "Lock Icon",
            tint = iconColor,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .clickable {
                    onClick()
                }
        )
    }
}

@Preview(device = "id:wearos_small_round")
@Composable
fun LockIconPreview() {
    var isLocked by remember { mutableStateOf(false) }
    LockIcon(isLocked = isLocked) { isLocked = !isLocked }
}

fun Modifier.gesturesDisabled(disabled: Boolean = true) =
    if (disabled) {
        pointerInput(Unit) {
            awaitPointerEventScope {
                // we should wait for all new pointer events
                while (true) {
                    awaitPointerEvent(pass = PointerEventPass.Initial)
                        .changes
                        .forEach(PointerInputChange::consume)
                }
            }
        }
    } else {
        this
    }