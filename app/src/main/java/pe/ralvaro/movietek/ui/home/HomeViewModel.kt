package pe.ralvaro.movietek.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepository
import pe.ralvaro.movietek.domain.home.MoviePaginationAccessUseCase
import pe.ralvaro.movietek.ui.InitialView
import pe.ralvaro.movietek.utils.successOr
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferenceStoreRepository: PreferenceStoreRepository,
    moviePaginationAccessUseCase: MoviePaginationAccessUseCase,
) : ViewModel() {

    val moviePagingFlow = moviePaginationAccessUseCase(Unit).map {
        it.successOr(PagingData.empty())
    }.cachedIn(viewModelScope)

    fun logout() {
        viewModelScope.launch {
            preferenceStoreRepository.updateInitialView(InitialView.Login)
        }
    }

}