package pe.ralvaro.movietek.data.repositories

import pe.ralvaro.movietek.data.remote.FakeApiLogin
import pe.ralvaro.movietek.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single point of access to the fake server login point
 */
@Singleton
class CredentialsDataRepository @Inject constructor(
    private val fakeApiLogin: FakeApiLogin
) {

    suspend fun makeLoginInServer(email: String, password: String): Result<Any> {
        return fakeApiLogin.putLogin(email, password)
    }

    fun getUsername() : String {
        return fakeApiLogin.currentUsername()
    }

}