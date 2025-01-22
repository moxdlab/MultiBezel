package io.multibezel.model

interface MusicPlayerActions {
    var currentVolume: Float

    fun play()

    fun pause()

    fun stop()

    fun nextSong()

    fun previousSong()

    fun isPlaying(): Boolean

    fun changePitch(newPitch: Float)

    fun changeSpeed(newSpeed: Float)

    fun changeVolume(volumeChange: Float)
}