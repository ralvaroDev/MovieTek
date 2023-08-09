package pe.ralvaro.movietek.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pe.ralvaro.movietek.domain.home.CloseSessionUseCase
import pe.ralvaro.movietek.domain.home.FixedPagingActionUseCase
import pe.ralvaro.movietek.domain.home.FixedPagingUseCase
import pe.ralvaro.movietek.domain.home.GetUsernameFromCurrentSessionUseCase
import pe.ralvaro.movietek.domain.home.MoviePaginationAccessUseCase
import pe.ralvaro.movietek.utils.successOr
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    moviePaginationAccessUseCase: MoviePaginationAccessUseCase,
    getUsernameFromCurrentSessionUseCase: GetUsernameFromCurrentSessionUseCase,
    private val closeSessionUseCase: CloseSessionUseCase,
    fixedPagingUseCase: FixedPagingUseCase,
    private val fixedPagingActionUseCase: FixedPagingActionUseCase
) : ViewModel() {

    // We create this channel as a notifier, in case session is closed
    // We can validate that from server from an init
    private val _intentFinishChannel = Channel<Unit>()
    val intentFinishChannel = _intentFinishChannel.receiveAsFlow()

    // This fix the weird behavior of paging library
    val isPagingFixed = fixedPagingUseCase(Unit).map {
        it.successOr(false)
    }

    // Update fixed paging to avoid unwanted scrolls
    fun notifyPagingFixed() {
        viewModelScope.launch {
            fixedPagingActionUseCase(Unit)
        }
    }

    val userName = getUsernameFromCurrentSessionUseCase(Unit).map { it.successOr("") }

    // This represent the paging flow with the data
    val moviePagingFlow = moviePaginationAccessUseCase(Unit).map {
        it.successOr(PagingData.empty())
    }.cachedIn(viewModelScope)

    fun closeSession() {
        viewModelScope.launch {
            closeSessionUseCase(Unit)
            _intentFinishChannel.send(Unit)
        }
    }

}