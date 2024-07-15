package de.thkoeln.modi.multibezel.model

interface MusicPlayerActions {
    fun play()

    fun pause()

    fun stop()

    fun nextSong()

    fun previousSong()

    fun isPlaying(): Boolean

    fun changePitch(newPitch: Float)

    fun changeSpeed(newSpeed: Float)

}