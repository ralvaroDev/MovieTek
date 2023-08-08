package pe.ralvaro.movietek.domain

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.movietek.util.MainCoroutineRule
import pe.ralvaro.movietek.utils.Result.Error

/**
 * Test for [UseCase]
 */
@ExperimentalCoroutinesApi
class UseCaseTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    // Get an override dispatcher
    private val testDispatcher = coroutineRule.testDispatcher

    @Test
    fun useCaseException_returnResultError() = runTest {
        val useCase = ExceptionThrowerUseCase(testDispatcher)
        val result = useCase(Unit)

        assertThat(result).isInstanceOf(Error::class.java)
    }

    class ExceptionThrowerUseCase(dispatcher: CoroutineDispatcher): UseCase<Unit, Unit>(dispatcher) {
        override suspend fun execute(parameters: Unit) {
            throw Exception("Test useCase exception")
        }
    }

}