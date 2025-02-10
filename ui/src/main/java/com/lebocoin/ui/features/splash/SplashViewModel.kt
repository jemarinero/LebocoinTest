package com.lebocoin.ui.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lebocoin.domain.model.ErrorType
import com.lebocoin.domain.model.doIfFailure
import com.lebocoin.domain.usecase.DownloadInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(
    private val downloadInformationUseCase: DownloadInformationUseCase
) : ViewModel() {

    private val _downloadState =
        MutableStateFlow(false)
    val downloadState: StateFlow<Boolean>
        get() = _downloadState.asStateFlow()

    private val _errorState = MutableStateFlow(OnError())
    val errorState: StateFlow<OnError>
        get() = _errorState.asStateFlow()

    init {
        downloadInformation()
    }

    private fun downloadInformation() {
        downloadInformationUseCase.execute(Unit)
            .catch {
                _errorState.update { OnError(ErrorType.UnknownError) }
                _downloadState.update { true }
            }
            .onEach {
                it.doIfFailure { error ->
                    _errorState.update { OnError(error) }
                }
                _downloadState.update { true }
            }
            .launchIn(viewModelScope)

    }

    fun clearError() {
        _errorState.update { OnError() }
    }

    data class OnError(
        val errorType: ErrorType? = null
    )
}