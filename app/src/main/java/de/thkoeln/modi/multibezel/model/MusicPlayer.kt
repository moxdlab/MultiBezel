package de.thkoeln.modi.multibezel.model

import android.content.res.AssetManager
import android.media.MediaPlayer
import android.util.Log

class MusicPlayer(private val assetManager: AssetManager) {

    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var currentSongIndex: Int = 0
    val songs: MutableList<String> = mutableListOf()

    init {
        loadSongsFromAssets()
        if (songs.isNotEmpty()) {
            currentSongIndex = 0
            prepareSong(songs[currentSongIndex])
            //mediaPlayer.setOnCompletionListener { nextSong() }
        }
    }

    private fun loadSongsFromAssets() {
        try {
            val assetFiles = assetManager.list("")
            assetFiles?.forEach { fileName ->
                if (fileName.endsWith(".mp3", true)) {
                    songs.add(fileName)
                }
            }
        } catch (e: Exception) {
            Log.e("MusicPlayer", "Error loading songs from assets: ${e.message}")
        }
    }

    private fun prepareSong(songName: String) {
        try {
            mediaPlayer.reset()
            val assetFileDescriptor = assetManager.openFd(songName)
            mediaPlayer.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
            assetFileDescriptor.close()
            mediaPlayer.prepare()
        } catch (e: Exception) {
            Log.e("MusicPlayer", "Error preparing song: ${e.message}")
        }
    }

    fun play() {
        if (!isPlaying()) {
            mediaPlayer.start()
        }
    }

    fun pause() {
        if (isPlaying()) {
            mediaPlayer.pause()
        }
    }

    fun stop() {
        mediaPlayer.stop()
        mediaPlayer.reset()
    }

    fun nextSong() {
        currentSongIndex = (currentSongIndex + 1) % songs.size
        stop()
        prepareSong(songs[currentSongIndex])
        play()
    }

    fun previousSong() {
        currentSongIndex = (currentSongIndex - 1 + songs.size) % songs.size
        stop()
        prepareSong(songs[currentSongIndex])
        play()
    }

    fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    // TODO: Add other methods like seekTo, setVolume, ...
}