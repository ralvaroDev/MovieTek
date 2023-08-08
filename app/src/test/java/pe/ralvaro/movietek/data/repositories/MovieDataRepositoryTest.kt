package pe.ralvaro.movietek.data.repositories

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import pe.ralvaro.movietek.data.models.UpcomingMovies
import pe.ralvaro.movietek.data.network.models.NetUpcomingContainer
import pe.ralvaro.movietek.data.network.models.toDomainModel
import pe.ralvaro.movietek.util.TestData

/**
 * Test for [MovieDataRepository]
 */
class MovieDataRepositoryTest {

    private lateinit var repo: MovieDataRepository

    @Test
    fun obtainOfflineData_remoteResponseAreUsedOverLocalData() = runTest {
        repo = MovieDataRepository(
            remoteMovieSource = FakeOkRemoteMovieSource(),
            localMovieSource = FakeLocalMovieSource()
        )

        repo.downloadMoviesFromServer(1)

        val response = repo.getOfflineData()
        assertThat(response).isEqualTo(TestData.netUpcomingContainer.toDomainModel())
        assertThat(repo.dataSource).isEqualTo(DataTagSource.NETWORK)
    }

    @Test
    fun obtainOfflineData_remoteWrong_localDataIsUsed() = runTest {
        repo = MovieDataRepository(
            remoteMovieSource = FakeBrokeRemoteMovieSource(),
            localMovieSource = FakeOkLocalMovieSource()
        )

        try {
            repo.downloadMoviesFromServer(1)
        } catch (e:Exception) {
            assertThat(e).isNotNull()
        }

        val localTest = UpcomingMovies(
            page = 3401, movieList = listOf(), totalPages = 9509, totalResults = 6626
        )

        val response = repo.getOfflineData()
        assertThat(response).isEqualTo(localTest)
        assertThat(repo.dataSource).isEqualTo(DataTagSource.LOCAL)
    }

}

class FakeOkLocalMovieSource : MovieDataSource {
    override suspend fun getUpcomingMovies(page: Int): NetUpcomingContainer {
        return TestData.netUpcomingContainer
    }
}

class FakeLocalMovieSource : MovieDataSource {
    override suspend fun getUpcomingMovies(page: Int): NetUpcomingContainer? {
        return null
        //return TestData.netUpcomingContainer
    }
}

class FakeOkRemoteMovieSource : MovieDataSource {

    override suspend fun getUpcomingMovies(page: Int): NetUpcomingContainer {
        return TestData.netUpcomingContainer
    }

}

class FakeBrokeRemoteMovieSource : MovieDataSource {

    override suspend fun getUpcomingMovies(page: Int): NetUpcomingContainer? {
        throw Exception("404")
    }

}
