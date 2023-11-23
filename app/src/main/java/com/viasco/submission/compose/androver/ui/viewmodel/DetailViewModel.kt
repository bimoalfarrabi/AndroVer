package com.viasco.submission.compose.androver.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viasco.submission.compose.androver.data.AndroVerRepository
import com.viasco.submission.compose.androver.model.AndroVer
import com.viasco.submission.compose.androver.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: AndroVerRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<AndroVer>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<AndroVer>> get() = _uiState

    fun getAndroVerById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getAndroVerById(id))
    }

    fun updatePlayer(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateAndroVer(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getAndroVerById(id)
            }
    }
}