package pe.ralvaro.movietek.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pe.ralvaro.movietek.domain.login.LoginUseCase
import pe.ralvaro.movietek.utils.PasswordValidator
import pe.ralvaro.movietek.utils.Result
import pe.ralvaro.movietek.utils.UsernameValidator
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginStatusChannel = Channel<LoginStatus>()
    val loginStatusChannel = _loginStatusChannel.receiveAsFlow()

    private var userName: String = ""
    private var password: String = ""

    fun setUsername(userName: String) {
        this.userName = userName
    }

    fun setPassword(password: String) {
        this.password = password
    }

    // This func handle validation of credentials
    fun login() {
        viewModelScope.launch {
            _loginStatusChannel.send(LoginStatus.Loading)

            val isUserNameValid = UsernameValidator.isValid(userName)
            val isPasswordValid = PasswordValidator.isValid(password)

            if (isUserNameValid && isPasswordValid) {
                executeLogin()
            } else {
                _loginStatusChannel.send(LoginStatus.Error("Invalid credentials"))
            }
        }
    }

    // When credentials are valid, we execute the login
    private suspend fun executeLogin() {
        loginUseCase(Pair(userName, password)).first().let {
            Timber.d("Login result: $it")
            when (it) {
                is Result.Success -> _loginStatusChannel.send(LoginStatus.Success)
                else -> _loginStatusChannel.send(LoginStatus.Error("Invalid credentials"))
            }
        }
    }

}

// Then can add types to handle it internally in the custom view
sealed interface LoginStatus {
    data class Error(val message: String) : LoginStatus
    object Success : LoginStatus
    object Loading : LoginStatus
}


