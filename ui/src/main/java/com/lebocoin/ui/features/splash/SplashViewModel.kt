package com.lebocoin.ui.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        downloadInformation()
    }

    private fun downloadInformation() {
        downloadInformationUseCase.execute(Unit)
            .catch { _downloadState.update { true } }
            .onEach { _downloadState.update { true } }
            .launchIn(viewModelScope)

    }
}