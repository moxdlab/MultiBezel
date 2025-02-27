package io.multibezel.model

import android.content.res.AssetManager
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MusicPlayer(private val assetManager: AssetManager) : MusicPlayerActions {

    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var currentSongIndex: Int = 0
    private val songs: MutableList<Song> = mutableListOf()
    private val maxVolume = 1f
    override var currentVolume = 0.5F
        set(value) = if (value in 0F..<maxVolume) field = value else if (value <= 0F) field =
            0F else { }

    private val _currentSong = MutableLiveData<Song?>(null)
    val currentSong: LiveData<Song?> = _currentSong

    private val _progress = MutableLiveData(0f)
    val progress: LiveData<Float> = _progress

    private val scope = CoroutineScope(Job() + Dispatchers.IO)
    private var progressJob: Job? = null

    init {
        loadSongsFromAssets()
        if (songs.isNotEmpty()) {
            currentSongIndex = 0
            _currentSong.postValue(songs[currentSongIndex])
            prepareSong(songs[currentSongIndex])
        }
    }

    private fun loadSongsFromAssets() {
        try {
            val assetFiles = assetManager.list("")
            assetFiles?.forEach { fileName ->
                if (fileName.endsWith(".mp3", true)) {
                    val pattern = Regex("^(.*?) - (.*)\\.mp3")
                    val matchResult = pattern.find(fileName)

                    val song = if (matchResult != null) {
                        val title = matchResult.groups[2]?.value ?: "Unknown Title"
                        val artist = matchResult.groups[1]?.value ?: "Unknown Artist"
                        Song(title, artist, fileName)
                    } else {
                        Song(fileName, "Unknown Artist", fileName)
                    }

                    songs.add(song)
                }
            }
        } catch (e: Exception) {
            Log.e("MusicPlayer", "Error loading songs from assets: ${e.message}")
        }
    }

    private fun prepareSong(song: Song) {
        try {
            mediaPlayer.reset()
            val assetFileDescriptor = assetManager.openFd(song.filePath)
            mediaPlayer.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
            _currentSong.postValue(song)
            mediaPlayer.setOnCompletionListener {
                stopProgressUpdates()
                nextSong()
            }
            assetFileDescriptor.close()
            mediaPlayer.prepare()
        } catch (e: Exception) {
            Log.e("MusicPlayer", "Error preparing song: ${e.message}")
        }
    }

    private fun startProgressUpdates() {
        progressJob?.cancel()
        progressJob = scope.launch {
            while (isActive) {
                if (mediaPlayer.isPlaying) {
                    val currentPosition = mediaPlayer.currentPosition.toFloat()
                    val totalDuration = mediaPlayer.duration.toFloat()
                    val progress = if (totalDuration > 0) currentPosition / totalDuration else 0f
                    _progress.postValue(progress)
                }
                delay(100)
            }
        }
    }


    private fun stopProgressUpdates() {
        progressJob?.cancel()
    }

    override fun play() {
        if (!isPlaying()) {
            try {
                startProgressUpdates()
                mediaPlayer.start()
            } catch (e: Exception) {
                Log.e("MusicPlayer", "Error playing song: ${e.message}")
            }
        }
    }

    override fun pause() {
        if (isPlaying()) {
            stopProgressUpdates()
            mediaPlayer.pause()
        }
    }

    override fun stop() {
        stopProgressUpdates()
        _progress.postValue(0F)
        mediaPlayer.stop()
        mediaPlayer.reset()
    }

    override fun nextSong() {
        currentSongIndex = (currentSongIndex + 1) % songs.size
        stop()
        prepareSong(songs[currentSongIndex])
        play()
    }

    override fun previousSong() {
        currentSongIndex = (currentSongIndex - 1 + songs.size) % songs.size
        stop()
        prepareSong(songs[currentSongIndex])
        play()
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun changeSpeed(newSpeed: Float) {
        val currentSpeed = mediaPlayer.playbackParams.speed
        val params = PlaybackParams()
        params.setSpeed(currentSpeed + newSpeed)
        mediaPlayer.playbackParams = params
    }

    override fun changeVolume(volumeChange: Float) {
        currentVolume += volumeChange
        mediaPlayer.setVolume(currentVolume, currentVolume)
    }

    override fun changePitch(newPitch: Float) {
        val currentPitch = mediaPlayer.playbackParams.pitch
        val params = PlaybackParams()
        params.setPitch(currentPitch + newPitch)
        mediaPlayer.playbackParams = params
    }
}