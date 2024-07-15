package de.thkoeln.modi.multibezel.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.thkoeln.modi.multibezel.model.MusicPlayer
import de.thkoeln.modi.multibezel.model.MusicPlayerActions
import de.thkoeln.modi.multibezel.model.Song

class CustomMusicPlayerViewModel : ViewModel() {
    private val _musicPlayerActions: MutableLiveData<MusicPlayerActions> = MutableLiveData(null)
    val musicPlayerActions: LiveData<MusicPlayerActions> = _musicPlayerActions

    private val _currentSong: MutableLiveData<Song?> = MutableLiveData(null)
    val currentSong: LiveData<Song?> = _currentSong

    private val _progress: MutableLiveData<Float> = MutableLiveData(0F)
    val progress: LiveData<Float> = _progress

    private val _isPlaying: MutableLiveData<Boolean> = MutableLiveData(false)
    val isPlaying: LiveData<Boolean?> = _isPlaying

    fun initMusicPlayer(context: Context) {
        if (_musicPlayerActions.value == null) {
            val musicPlayer = MusicPlayer(context.assets)
            _musicPlayerActions.value = musicPlayer
            musicPlayer.currentSong.observeForever { _currentSong.value = it }
            musicPlayer.progress.observeForever { _progress.value = it }
        }
    }

    fun playPause() {
        if (_musicPlayerActions.value?.isPlaying() == true) {
            _isPlaying.postValue(false)
            _musicPlayerActions.value?.pause()
        } else {
            _isPlaying.postValue(true)
            _musicPlayerActions.value?.play()
        }
    }

    fun onNextSong() {
        _musicPlayerActions.value?.nextSong()
        _isPlaying.postValue(true)
    }

    fun onPreviousSong() {
        _musicPlayerActions.value?.previousSong()
        _isPlaying.postValue(true)
    }
    fun increaseSpeed() {
        _musicPlayerActions.value?.changeSpeed(0.10f)
    }
    fun increasePitch() {
        _musicPlayerActions.value?.changePitch(0.10f)
    }
}

