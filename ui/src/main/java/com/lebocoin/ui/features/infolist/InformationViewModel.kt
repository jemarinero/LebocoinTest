package com.lebocoin.ui.features.infolist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lebocoin.domain.model.Information
import com.lebocoin.domain.model.doIfFailure
import com.lebocoin.domain.model.doIfSuccess
import com.lebocoin.domain.usecase.GetInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class InformationViewModel
@Inject
constructor(
    private val getInformationUseCase: GetInformationUseCase
) : ViewModel() {

    var state by mutableStateOf(UiState())
    private set

    fun getInformation() {
        resetError()
        resetLoading()
        getInformationUseCase.execute(Unit)
            .catch { state = state.copy(isError = true) }
            .onStart { state = state.copy(isLoading = true)}
            .onEach { res ->
                resetLoading()
                res.doIfSuccess { data ->
                    state = state.copy(data = data)
                }
                res.doIfFailure{
                    state = state.copy(isError = true)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun resetError() {
        state = state.copy(isError = false)
    }
    private fun resetLoading() {
        state = state.copy(isLoading = false)
    }

    data class UiState(
        val isLoading: Boolean = false,
        val data: List<Information> = emptyList(),
        val isError: Boolean = false
    )
}