package de.thkoeln.modi.multibezel.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thkoeln.modi.multibezel.model.Action
import de.thkoeln.modi.multibezel.model.ActionHandler
import de.thkoeln.modi.multibezel.model.MusicPlayer
import de.thkoeln.modi.multibezel.model.MusicPlayerActions
import de.thkoeln.modi.multibezel.model.ParsedData
import de.thkoeln.modi.multibezel.model.Receiver
import de.thkoeln.modi.multibezel.model.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CustomMusicPlayerViewModel : ViewModel() {
    private val _musicPlayerActions: MutableLiveData<MusicPlayerActions> = MutableLiveData(null)
    val musicPlayerActions: LiveData<MusicPlayerActions> = _musicPlayerActions

    private val _currentSong: MutableLiveData<Song?> = MutableLiveData(null)
    val currentSong: LiveData<Song?> = _currentSong

    private val _progress: MutableLiveData<Float> = MutableLiveData(0F)
    val progress: LiveData<Float> = _progress

    private val _isPlaying: MutableLiveData<Boolean> = MutableLiveData(false)
    val isPlaying: LiveData<Boolean?> = _isPlaying

    private val _incomingData = Receiver().receiveData().map { ParsedData(it) }

    init {
        startCollectingData()
    }

    private fun startCollectingData() {
        val actionHandler = ActionHandler()
        viewModelScope.launch(Dispatchers.IO) {
            _incomingData.collect {
                when (actionHandler.handleParsedData(it)) {
                    Action.None -> {}
                    Action.PlayPause -> playPause()
                    Action.DecreaseVolume -> decreaseVolume()
                    Action.IncreaseVolume -> increaseVolume()
                    Action.NextSong -> nextSong()
                    Action.PreviousSong -> previousSong()
                }
            }
        }
    }

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

    fun nextSong() {
        _musicPlayerActions.value?.nextSong()
        _isPlaying.postValue(true)
    }

    fun previousSong() {
        _musicPlayerActions.value?.previousSong()
        _isPlaying.postValue(true)
    }

    fun increaseSpeed() {
        _musicPlayerActions.value?.changeSpeed(0.10f)
    }

    fun increasePitch() {
        _musicPlayerActions.value?.changePitch(0.10f)
    }

    fun decreaseVolume(){
        _musicPlayerActions.value?.changeVolume(-0.05f)
    }

    fun increaseVolume(){
        _musicPlayerActions.value?.changeVolume(0.05f)
    }
}

