package de.thkoeln.modi.multibezel.model

import android.util.Log


sealed class Action(val name: String) {
    data object PlayPause : Action("PLAY_PAUSE")
    data object Skip : Action("SKIP")
    data object Previous : Action("PREVIOUS")
    data object None : Action("NONE")
}

class ActionHandler {
    private val parsedDataHistory = mutableListOf<ParsedData>()
    private val actionHistory = mutableListOf<Action>()

    fun handleParsedData(parsedData: ParsedData): Action {
        val action = when {
            parsedDataHistory.lastOrNull()?.numberOfFingers == 0 && parsedData.numberOfFingers > 0 -> {
                Action.PlayPause
            }
            else -> Action.None
        }

        actionHistory.add(action)
        Log.d("DATA", "Action: ${action.name}, Fingers: ${parsedData.numberOfFingers}")
        parsedDataHistory.add(parsedData)
        return action
    }
}