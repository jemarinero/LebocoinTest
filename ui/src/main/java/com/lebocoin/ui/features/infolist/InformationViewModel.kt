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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InformationViewModel
@Inject
constructor(
    private val getInformationUseCase: GetInformationUseCase
) : ViewModel() {

    var infoList = MutableStateFlow<List<Information>>(emptyList())
        private  set

    private val _pagingState =
        MutableStateFlow(PaginationState.LOADING)
    val pagingState: StateFlow<PaginationState>
        get() = _pagingState.asStateFlow()

    private var page = 0
    var canPaginate by mutableStateOf(false)

    fun getInformation() {
        if (page == INITIAL_PAGE || (page != INITIAL_PAGE && canPaginate) && _pagingState.value == PaginationState.REQUEST_INACTIVE) {
            _pagingState.update { if (page == INITIAL_PAGE) PaginationState.LOADING else PaginationState.PAGINATING }
        }
        getInformationUseCase.execute(
            GetInformationUseCase.Pagination(
            limit = PAGE_SIZE,
            offset = page * PAGE_SIZE
        ))
            .onEach { res ->
                res.doIfSuccess { data ->
                    canPaginate = data.size == PAGE_SIZE
                    if (page == INITIAL_PAGE) {
                        if (data.isEmpty()) {
                            _pagingState.update { PaginationState.EMPTY }
                            return@onEach
                        }
                        infoList.value = emptyList()
                        infoList.value = data
                    } else {
                        infoList.value += data
                    }

                    _pagingState.update { PaginationState.REQUEST_INACTIVE }

                    if (canPaginate) {
                        page++
                    }

                    if (!canPaginate) {
                        _pagingState.update { PaginationState.PAGINATION_EXHAUST }
                    }
                }
                res.doIfFailure{
                    _pagingState.update { if (page == INITIAL_PAGE) PaginationState.ERROR else PaginationState.PAGINATION_EXHAUST }
                }
            }
            .launchIn(viewModelScope)
    }

    fun clearPaging() {
        page = INITIAL_PAGE
        _pagingState.update { PaginationState.LOADING }
        canPaginate = false
    }

    data class UiState(
        val isLoading: Boolean = false,
        val data: List<Information> = emptyList(),
        val isError: Boolean = false
    )

    enum class PaginationState {
        REQUEST_INACTIVE,
        LOADING,
        PAGINATING,
        ERROR,
        PAGINATION_EXHAUST,
        EMPTY,
    }

    companion object {
        const val PAGE_SIZE = 20
        const val INITIAL_PAGE = 0
    }
}