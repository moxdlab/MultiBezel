package de.thkoeln.modi.multibezel.model

import kotlin.math.absoluteValue


sealed class Action(val name: String) {
    data object PlayPause : Action("PLAY_PAUSE")
    data object PreviousSong : Action("PREVIOUS_SONG")
    data object NextSong : Action("NEXT_SONG")
    data object IncreaseVolume : Action("INCREASE_VOLUME")
    data object DecreaseVolume : Action("DECREASE_VOLUME")
    data object None : Action("NONE")
}

class ActionHandler {
    private val parsedDataHistory = mutableListOf<ParsedData>()
    private val lastParsedData
        get() = parsedDataHistory.lastOrNull()
    private val actionHistory = mutableListOf<Action>()
    private val lastAction
        get() = actionHistory.lastOrNull()

    private var currentDeltaSum = 0F
    private val songThreshold = 100F
    private val volumeThreshold = 200F

    private fun handleGesture(
        parsedData: ParsedData,
        fingerCount: Int,
        threshold: Float,
        positiveAction: Action,
        negativeAction: Action
    ): Action {
        if (parsedData.numberOfFingers != fingerCount) return Action.None

        val delta = parsedData.calculateDeltaToOtherData(lastParsedData)
        if (lastParsedData?.numberOfFingers != fingerCount) currentDeltaSum = 0F
        currentDeltaSum += delta

        return if (currentDeltaSum.absoluteValue >= threshold) {
            currentDeltaSum = 0F
            if (delta < 0f) negativeAction else positiveAction
        } else Action.None
    }

    fun handleParsedData(parsedData: ParsedData): Action {
        val action = when {
            lastParsedData?.numberOfFingers != 3 && parsedData.numberOfFingers >= 3 -> {
                Action.PlayPause
            }

            else -> {
                when (parsedData.numberOfFingers) {
                    2 -> handleGesture(
                        parsedData,
                        2,
                        songThreshold,
                        Action.NextSong,
                        Action.PreviousSong
                    )

                    1 -> handleGesture(
                        parsedData,
                        1,
                        volumeThreshold,
                        Action.IncreaseVolume,
                        Action.DecreaseVolume
                    )

                    else -> {
                        currentDeltaSum = 0F
                        Action.None
                    }
                }
            }
        }

        actionHistory.add(action)
        parsedDataHistory.add(parsedData)
        return action
    }
}