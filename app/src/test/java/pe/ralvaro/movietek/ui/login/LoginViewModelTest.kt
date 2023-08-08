package pe.ralvaro.movietek.ui.login

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import pe.ralvaro.movietek.data.network.FakeServerSource
import pe.ralvaro.movietek.data.repositories.CredentialsDataRepository
import pe.ralvaro.movietek.domain.login.LoginUseCase
import pe.ralvaro.movietek.ui.LauncherViewModelTest
import pe.ralvaro.movietek.util.FakePreferenceStoreRepository
import pe.ralvaro.movietek.util.MainCoroutineRule
import pe.ralvaro.movietek.util.TestData

/**
 * Test for [LoginViewModel]
 */
@ExperimentalCoroutinesApi
class LoginViewModelTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(
            loginUseCase = LoginUseCase(
                credentialsDataRepository = CredentialsDataRepository(
                    fakeApiLogin = FakeServerSource
                ),
                preferenceStoreRepository = FakePreferenceStoreRepository(),
                dispatcher = coroutineRule.testDispatcher
            )
        )
    }

    @Test
    fun loginViewModel_inputInvalidUserName_showInvalidCredentials() = runTest {
        // user types invalid userName but correct password
        viewModel.setUsername(TestData.userNameMalformed)
        viewModel.setPassword(TestData.passwordOK)

        // user clicks on login button
        viewModel.login()

        val result = viewModel.loginStatusChannel
        // First value is Loading
        assertThat(result.first()).isInstanceOf(LoginStatus.Loading::class.java)
        // Second value is Error
        assertThat(result.first()).isInstanceOf(LoginStatus.Error::class.java)
    }

    @Test
    fun loginViewModel_inputInvalidPassword_showInvalidCredentials() = runTest {
        // user types invalid userName but correct password
        viewModel.setUsername(TestData.userNameOK)
        viewModel.setPassword(TestData.passwordWrong)

        // user clicks on login button
        viewModel.login()

        val result = viewModel.loginStatusChannel
        // First value is Loading
        assertThat(result.first()).isInstanceOf(LoginStatus.Loading::class.java)
        // Second value is Error
        assertThat(result.first()).isInstanceOf(LoginStatus.Error::class.java)
    }

    @Test
    fun loginViewModel_inputValidCredentials_showSuccess() = runTest {
        // user types invalid userName but correct password
        viewModel.setUsername(TestData.userNameOK)
        viewModel.setPassword(TestData.passwordOK)

        // user clicks on login button
        viewModel.login()

        val result = viewModel.loginStatusChannel
        // First value is Loading
        assertThat(result.first()).isInstanceOf(LoginStatus.Loading::class.java)
        // Second value is Success
        assertThat(result.first()).isInstanceOf(LoginStatus.Success::class.java)
    }

}