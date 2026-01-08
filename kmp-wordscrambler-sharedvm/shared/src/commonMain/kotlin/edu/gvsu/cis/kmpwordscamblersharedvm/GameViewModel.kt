package edu.gvsu.cis.kmpwordscamblersharedvm

import com.hoc081098.kmp.viewmodel.ViewModel
import com.hoc081098.kmp.viewmodel.wrapper.wrap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class GameViewModel: ViewModel() {
    private val wordStock = listOf("oxygen", "hydrogen", "calcium", "germanium", "helium",
        "carbon", "radon", "fluor", "barium", "chlor", "neon", "aluminium", "lithium", "silicon")
    private val _secretWord = MutableStateFlow<String?>(null)
    private val _letters = MutableStateFlow<List<String?>>(emptyList())
    private val _removedLetter = MutableStateFlow<String?>(null)
    private val _solved = MutableStateFlow(false)

    val sw = _secretWord.asStateFlow()
    val secretWord = _secretWord
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null).wrap()
    val letters = _letters
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList()).wrap()
    val removedLetter = _removedLetter
        .stateIn(viewModelScope, SharingStarted.Lazily, null).wrap()
    val solved = _solved
        .stateIn(viewModelScope, SharingStarted.Lazily, false).wrap()

    fun generateNewWord() {
        val newWord = wordStock.random().uppercase()
        _secretWord.update { newWord }
        _letters.update { newWord.toList().map{it.toString()}.shuffled() }
        _removedLetter.update { null }
        _solved.update { false }
    }

    fun clickAtPos(pos: Int) {
        if (_removedLetter.value == null) {
            _removedLetter.update { _letters.value[pos] }
            _letters.update {
                it.filterIndexed { lpos, ch ->
                    lpos != pos
                } .flatMapIndexed { cpos, ch ->
                    if (cpos == 0) listOf(null, ch, null)
                    else listOf(ch, null)
                }
            }
        }
        else {
            if (_letters.value[pos] != null) return
            _letters.update {
                it.mapIndexed { lpos, ch ->
                    if (lpos == pos) _removedLetter.value else ch
                }.filter { it != null }

            }
            _solved.update {
                _letters.value.joinToString("") == _secretWord.value
            }
            _removedLetter.update { null }
        }
    }
}