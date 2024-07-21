package de.thkoeln.modi.multibezel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thkoeln.modi.multibezel.model.ParsedData
import de.thkoeln.modi.multibezel.model.Receiver
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
            val index = when {
                position < 100 -> 1
                position < 200 -> 0
                position < 300 -> 7
                position < 400 -> 6
                position < 500 -> 5
                position < 600 -> 4
                position < 700 -> 3
                position < 800 -> 2
                else -> continue // Ignore values outside the defined ranges
            }
            newSensorStates[index] = 1
        }

        _sensorStates.postValue(newSensorStates)
    }
}