package de.thkoeln.modi.multibezel.model


class ParsedData(private val rawData: List<Int>) {
    val numberOfFingers = rawData.lastOrNull() ?: 0
    private val fingerPositions: List<Int>
        get() = (0 until numberOfFingers).map { rawData[it] }

    fun calculateDeltaToOtherData(otherParsedData: ParsedData?): Float {
        if(otherParsedData == null) return 0F
        val lowNumberOfFinger = minOf(this.numberOfFingers, otherParsedData.numberOfFingers)
        var deltaSum = 0F
        for (i in 0 until lowNumberOfFinger) {
            deltaSum += otherParsedData.fingerPositions[i] - this.fingerPositions[i]
        }
        if(lowNumberOfFinger == 0) return 0F
        return deltaSum / lowNumberOfFinger
    }
}