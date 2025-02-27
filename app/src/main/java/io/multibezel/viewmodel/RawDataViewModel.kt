package io.multibezel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.multibezel.model.ParsedData
import io.multibezel.model.Receiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RawDataViewModel : ViewModel() {
    private val _incomingData = Receiver().receiveData().onEach {
        updateSensorStates(ParsedData(it))
    }

    private val _sensorStates = MutableLiveData(listOf(0, 0, 0, 0, 0, 0, 0, 0))
    val sensorStates: LiveData<List<Int>> = _sensorStates

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _incomingData.collect{}
        }
    }

    private fun updateSensorStates(parsedData: ParsedData) {
        val newSensorStates = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0)

        for (position in parsedData.fingerPositions) {
            val index = when (position) {
                in -1..99 -> 1
                in 100..199 -> 0
                in 200..299 -> 7
                in 300..399 -> 6
                in 400..499 -> 5
                in 500..599 -> 4
                in 600..699 -> 3
                else -> 2
            }
            newSensorStates[index] = 1
        }

        _sensorStates.postValue(newSensorStates)
    }
}