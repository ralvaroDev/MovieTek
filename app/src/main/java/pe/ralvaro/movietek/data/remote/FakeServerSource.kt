package pe.ralvaro.movietek.data.remote

import kotlinx.coroutines.delay
import pe.ralvaro.movietek.utils.Result.*
import pe.ralvaro.movietek.utils.Result

interface FakeApiLogin {
    suspend fun putLogin(userName: String, password: String): Result<Any>
    fun currentUsername() : String
}

/**
 * This class simulates implementation of retrofit with server connection
 */
object FakeServerSource : FakeApiLogin {

    // Inserts a delay to simulate real connection
    override suspend fun putLogin(userName: String, password: String): Result<Any> {
        val response = checkCredentials(userName.lowercase(), password)
        delay(2000)

        return if (response) {
            Success(Any())
        } else {
            Error(Exception("Error trying to login, invalid credentials"))
        }
    }

   override fun currentUsername() : String {
        return databaseCredentials.first().userName
    }

    private fun checkCredentials(userName: String, password: String): Boolean {
        return databaseCredentials.find { it.userName == userName && it.password == password } != null
    }

    private val databaseCredentials = listOf(
        UserFile("admin", "Password*123")
    )
}

private data class UserFile(
    val userName: String,
    val password: String
)