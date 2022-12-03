package uk.co.jofaircloth.memring.ui.methodDisplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import uk.co.jofaircloth.memring.data.entities.MethodEntity
import uk.co.jofaircloth.memring.data.repository.MethodRepository

class MethodDisplayViewModel(
    private val repository: MethodRepository
) : ViewModel() {

    fun methodsByStage(stage: Int): LiveData<List<MethodEntity>> {
        return repository.methodsByStage(stage).asLiveData()
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

class MethodDisplayViewModelFactory(
    private val repository: MethodRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MethodDisplayViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MethodDisplayViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
