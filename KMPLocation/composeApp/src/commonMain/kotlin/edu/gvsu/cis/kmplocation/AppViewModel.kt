package edu.gvsu.cis.kmplocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel: ViewModel() {
    val lokaProvider by lazy { getLokationProvider()}
    private val _location = MutableStateFlow<Pair<Double, Double>?>(null)
    val location = _location.asStateFlow()

    fun getCurrentLocation() {
        viewModelScope.launch {
            _location.update {
                lokaProvider.getCurrentLocation()
            }
        }
    }
}