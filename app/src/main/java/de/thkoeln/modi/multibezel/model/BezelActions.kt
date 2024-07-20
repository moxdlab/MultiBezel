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
    private val songThreshold = 300F
    private val volumeThreshold = 200F

    fun handleParsedData(parsedData: ParsedData): Action {
        val action = when {
            lastParsedData?.numberOfFingers != 3 && parsedData.numberOfFingers >= 3 -> {
                Action.PlayPause
            }

            parsedData.numberOfFingers == 2 -> {
                val delta = parsedData.calculateDeltaToOtherData(lastParsedData)
                currentDeltaSum += delta
                if (currentDeltaSum.absoluteValue >= songThreshold) {
                    currentDeltaSum = 0F
                    if (delta < 0f) Action.PreviousSong else Action.NextSong
                } else Action.None
            }

            parsedData.numberOfFingers == 1 && (lastParsedData?.numberOfFingers == 1 || lastParsedData?.numberOfFingers == 0) -> {
                val delta = parsedData.calculateDeltaToOtherData(parsedDataHistory.lastOrNull())
                currentDeltaSum += delta
                if (currentDeltaSum.absoluteValue >= volumeThreshold) {
                    currentDeltaSum = 0F
                    if (delta < 0f) Action.DecreaseVolume else Action.IncreaseVolume
                } else Action.None
            }

            else -> {
                currentDeltaSum = 0F
                Action.None
            }
        }

        actionHistory.add(action)
        parsedDataHistory.add(parsedData)
        return action
    }
}