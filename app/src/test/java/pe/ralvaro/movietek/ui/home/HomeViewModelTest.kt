package pe.ralvaro.movietek.ui.home

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import pe.ralvaro.movietek.data.remote.FakeApiLogin
import pe.ralvaro.movietek.data.repositories.CredentialsDataRepository
import pe.ralvaro.movietek.domain.home.GetUsernameFromCurrentSessionUseCase
import pe.ralvaro.movietek.util.MainCoroutineRule
import pe.ralvaro.movietek.utils.Result

/**
 * Test for [HomeViewModel]
 */
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        val useCase = GetUsernameFromCurrentSessionUseCase(
            credentialsDataRepository = FakeCredentialsRepository(),
            coroutineRule.testDispatcher
        )
        viewModel = HomeViewModel(
            moviePaginationAccessUseCase = mock { },
            getUsernameFromCurrentSessionUseCase = useCase,
            closeSessionUseCase = mock { },
            fixedPagingUseCase = mock {  },
            fixedPagingActionUseCase = mock {  }
        )
    }

    @Test
    fun homeViewModel_getUsernameFromCurrentSession() = runTest {
        val usernameResult = viewModel.userName.first()
        assertThat(usernameResult).isEqualTo("admin")
    }

    @Test
    fun homeViewModel_closeSessionEmitsChannel() = runTest {
        viewModel.closeSession()
        val resultChannel = viewModel.intentFinishChannel.first()
        assertThat(resultChannel).isEqualTo(Unit)
    }

    class FakeCredentialsRepository : CredentialsDataRepository(TestApiServer())

    class TestApiServer : FakeApiLogin {
        override suspend fun putLogin(userName: String, password: String): Result<Any> {
            return mock {  }
        }

        override fun currentUsername(): String {
            return "admin"
        }
    }

}