package uk.co.jofaircloth.memring.ui.preferences

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import uk.co.jofaircloth.memring.data.entities.MethodPropertyEntity
import uk.co.jofaircloth.memring.data.repository.MethodPreferences
import uk.co.jofaircloth.memring.data.repository.MethodPreferencesRepository
import uk.co.jofaircloth.memring.ui.method.BlueLineUiModel
import uk.co.jofaircloth.memring.ui.method.DisplayBlueLine
import uk.co.jofaircloth.memring.ui.methodDisplay.MethodDisplayViewState

class PreferencesViewModel(
    private val methodPreferenceRepository: MethodPreferencesRepository
): ViewModel() {
    private val TAG = "PreferencesViewModel"

    val initialSetupEvent = liveData {
        emit(methodPreferenceRepository.fetchInitialPreferences())
    }

    private val methodPreferencesFlow = methodPreferenceRepository.methodPreferencesFlow
    
//    private val methodModelUiFlow = combine(
//        methodPreferencesFlow
//    ) { methodPreferences: BlueLineUiModel ->
//        return@combine(
//
//        )
//    }
    
    fun showText(show: Boolean) {
        viewModelScope.launch {
            methodPreferenceRepository.updateShowText(show)
        }
    }
}

class PreferencesViewModelFactory(
    private val methodPreferencesRepository: MethodPreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreferencesViewModel::class.java)) {
            @Suppress("UNCHECKED_CASE")
            return PreferencesViewModel(methodPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}