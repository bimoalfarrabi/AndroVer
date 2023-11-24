package com.viasco.submission.compose.androver.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viasco.submission.compose.androver.data.AndroVerRepository
import com.viasco.submission.compose.androver.model.AndroVer
import com.viasco.submission.compose.androver.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: AndroVerRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<AndroVer>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<AndroVer>>> get() = _uiState

    private val _query = mutableStateOf("")

    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchAndroVer(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updatePlayer(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateAndroVer(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}