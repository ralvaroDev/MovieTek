package pe.ralvaro.movietek.domain.login

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.movietek.data.remote.FakeServerSource
import pe.ralvaro.movietek.data.repositories.CredentialsDataRepository
import pe.ralvaro.movietek.ui.login.LoginStatus
import pe.ralvaro.movietek.util.FakePreferenceStoreRepository
import pe.ralvaro.movietek.util.MainCoroutineRule
import pe.ralvaro.movietek.util.TestData
import pe.ralvaro.movietek.utils.Result.Error
import pe.ralvaro.movietek.utils.Result.Success

/**
 * Test for [LoginUseCase]
 */
@ExperimentalCoroutinesApi
class LoginUseCaseTest {

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Test
    fun loginUseCase_correctEmailAndPassword_returnResultSuccess() = runTest {

        val credentialsDataRepository = CredentialsDataRepository(FakeServerSource)

        val useCase = LoginUseCase(
            credentialsDataRepository = credentialsDataRepository,
            preferenceStoreRepository = FakePreferenceStoreRepository(),
            dispatcher = coroutineRule.testDispatcher
        )

        val result = useCase(TestData.correctCredentials).first()

        assertThat(result).isEqualTo(Success(LoginStatus.Success))
    }

    @Test
    fun loginUseCase_invalidEmailAndPassword_returnResultError() = runTest {

            val credentialsDataRepository = CredentialsDataRepository(FakeServerSource)

            val useCase = LoginUseCase(
                credentialsDataRepository = credentialsDataRepository,
                preferenceStoreRepository = FakePreferenceStoreRepository(),
                dispatcher = coroutineRule.testDispatcher
            )

            val result = useCase(TestData.wrongCredentials).first()

            assertThat(result).isInstanceOf(Error::class.java)
    }




}