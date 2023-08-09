package pe.ralvaro.movietek.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import pe.ralvaro.movietek.data.models.Movie
import pe.ralvaro.movietek.data.repositories.MoviesRepository
import pe.ralvaro.movietek.domain.detail.GetMovieByIdUseCase
import pe.ralvaro.movietek.util.MainCoroutineRule
import pe.ralvaro.movietek.utils.Result
import pe.ralvaro.movietek.utils.data

/**
 * Test for [DetailViewModel]
 */
@ExperimentalCoroutinesApi
class DetailViewModelTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Test
    fun detailViewModelTest_validId_returnMovieDetails() = runTest {

        val useCase = GetMovieByIdUseCase(
            repository = FakeMovieRepository(),
            coroutineRule.testDispatcher
        )

        val viewModel = DetailViewModel(
            getMovieByIdUseCase = useCase,
            savedStateHandle = SavedStateHandle(
                initialState = mapOf(
                    "id_movie" to 3525
                )
            )
        )

        val result = viewModel.movieDetails.first()

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(result.data?.id).isEqualTo(FakeMovieRepository.savedMovies[1].id)

    }

    class FakeMovieRepository : MoviesRepository(mock {  }) {

        companion object {
            val savedMovies = listOf(
                Movie(
                    uuid = 7624,
                    id = 9445,
                    title = "iusto",
                    posterPath = null,
                    voteAverage = 2.3,
                    overview = "vero",
                    releaseDate = "mea"
                ),
                Movie(
                    uuid = 1502,
                    id = 3525,
                    title = "vituperata",
                    posterPath = null,
                    voteAverage = 6.7,
                    overview = "animal",
                    releaseDate = "quod"

                )
            )
        }

        override fun getMovie(idMovie: Int) = flow {
            emit(savedMovies.find { it.id == idMovie }!!)
        }
    }

}