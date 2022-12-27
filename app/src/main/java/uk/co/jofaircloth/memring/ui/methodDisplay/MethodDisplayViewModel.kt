package uk.co.jofaircloth.memring.ui.methodDisplay

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uk.co.jofaircloth.memring.data.entities.MethodPropertyEntity
import uk.co.jofaircloth.memring.data.repository.CollectionRepository

class MethodDisplayViewModel(
    private val repository: CollectionRepository
) : ViewModel() {

    private val TAG = "MethodDisplayViewModel"

    private val _selectedMethod = MutableStateFlow<MethodPropertyEntity?>(null)
    private val _predictions = mutableStateOf(ArrayList<MethodPropertyEntity?>())

    private val _state = MutableStateFlow(MethodDisplayViewState())

    val state: StateFlow<MethodDisplayViewState> get() = _state

    fun onMethodSelect(method: MethodPropertyEntity) {
        _selectedMethod.value = method
    }

    fun onSearchTextChange(searchText: String) {
        if (searchText.length >= 3) {
            val stage = searchText.filter { it.isDigit() }
            val text = searchText.filter { !it.isDigit() }

            viewModelScope.launch {
                combine(
                    // TODO use input of selected stage (if set!)
                    repository.methodsByName(
                        text,
                        if (stage.isNotEmpty() and (stage.length <= 2)) stage.toInt() else 0
                    )
                        .onEach { methods ->
                            if (methods.isNotEmpty() && _predictions.value == null) {
                                @Suppress("UNCHECKED_CAST")
                                _predictions.value = methods as ArrayList<MethodPropertyEntity?>
                            }
                        },
                    _selectedMethod
                ) { methods, selectedMethod ->
                    MethodDisplayViewState(
                        methods = methods,
                        selectedMethod = selectedMethod
                    )
                }.collect { _state.value = it }
            }
        }
    }

    /**
    TODO use, eg, when inserting favourite
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
    */
    fun insert(word: Word) = viewModelScope.launch {
    repository.insert(word)
    }
     */
}

data class MethodDisplayViewState(
    val selectedMethod: MethodPropertyEntity? = null,
    val methods: List<MethodPropertyEntity> = emptyList()
    // TODO probably have built method in here too
)

class MethodDisplayViewModelFactory(
    private val repository: CollectionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MethodDisplayViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MethodDisplayViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
