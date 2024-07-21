package de.thkoeln.modi.multibezel.model


class ParsedData(private val rawData: List<Int>) {
    val numberOfFingers = rawData.lastOrNull() ?: 0
    private val fingerPositions: List<Int>
        get() = (0 until numberOfFingers).map { rawData[it] }

    fun calculateDeltaToOtherData(otherParsedData: ParsedData?): Float {
        if(otherParsedData == null) return 0F
        if(otherParsedData.numberOfFingers != this.numberOfFingers) return 0F
        if(numberOfFingers == 0) return 0F
        var deltaSum = 0F
        for (i in 0 until numberOfFingers) {
            deltaSum += otherParsedData.fingerPositions[i] - this.fingerPositions[i]
        }
        return deltaSum / numberOfFingers
    }
}