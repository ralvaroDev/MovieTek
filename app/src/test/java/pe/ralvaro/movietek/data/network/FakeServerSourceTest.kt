package pe.ralvaro.movietek.data.network

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.movietek.util.MainCoroutineRule
import pe.ralvaro.movietek.util.TestData
import pe.ralvaro.movietek.utils.Result

/**
 * Test for [FakeServerSource]
 */
@ExperimentalCoroutinesApi
class FakeServerSourceTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Test
    fun putLogin_correctCredentials_resultSuccess() = runTest {

        val response = FakeServerSource.putLogin(
            TestData.userNameOK,
            TestData.passwordOK
        )

        assertThat(response).isInstanceOf(Result.Success::class.java)
    }

    @Test
    fun putLogin_invalidCredentials_resultError() = runTest {

        val response = FakeServerSource.putLogin(
            TestData.userNameWrong,
            TestData.passwordWrong
        )

        assertThat(response).isInstanceOf(Result.Error::class.java)
    }

}