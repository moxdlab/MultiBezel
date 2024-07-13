package de.thkoeln.modi.multibezel.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.thkoeln.modi.multibezel.model.MusicPlayer

class CustomMusicPlayerViewModel : ViewModel() {
    private val _musicPlayer: MutableLiveData<MusicPlayer> = MutableLiveData(null)


    fun initMediaPlayer(context: Context) {
        _musicPlayer.value = MusicPlayer(context.assets)
    }

    fun playPause(){
        if(_musicPlayer.value?.isPlaying() == true)
            _musicPlayer.value?.pause()
        else
            _musicPlayer.value?.play()
    }

    fun onNextSong(){
        _musicPlayer.value?.nextSong()
    }

    fun onPreviousSong(){
        _musicPlayer.value?.previousSong()
    }
}

