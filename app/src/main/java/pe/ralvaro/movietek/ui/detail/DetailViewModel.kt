package pe.ralvaro.movietek.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.ralvaro.movietek.data.models.Movie
import pe.ralvaro.movietek.domain.detail.GetMovieByIdUseCase
import pe.ralvaro.movietek.utils.Result
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _movieDetails = MutableStateFlow<Result<Movie>>(Result.Loading)
    val movieDetails: StateFlow<Result<Movie>> = _movieDetails.asStateFlow()

    init {
        viewModelScope.launch {
            _movieDetails.update {
                // Obtain the args passed by navigation
                getMovieByIdUseCase(args.idMovie).first()
            }
        }
    }

}
