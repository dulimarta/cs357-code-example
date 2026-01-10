package edu.gvsu.cis.kmp_splitui_sharedvm

import com.hoc081098.kmp.viewmodel.ViewModel
import com.hoc081098.kmp.viewmodel.wrapper.NonNullStateFlowWrapper
import com.hoc081098.kmp.viewmodel.wrapper.wrap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _count = MutableStateFlow<Int>(0)
    val count: NonNullStateFlowWrapper<Int> = _count
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0
        ).wrap()

    val x = _count.asStateFlow()
    fun add() {
        _count.update { it + 1 }
    }
}