package pe.ralvaro.movietek.ui

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepository
import pe.ralvaro.movietek.domain.launcher.InitialViewUseCase
import pe.ralvaro.movietek.util.MainCoroutineRule


/**
 * Test for [LauncherViewModel]
 */
@ExperimentalCoroutinesApi
class LauncherViewModelTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Test
    fun launchDestination_userIsLogged_emitInitialViewMain() = destinationTest(
        userIsLogged = true,
        expectedDestination = InitialView.Main
    )

    @Test
    fun launchDestination_userIsNotLogged_emitInitialViewLogin() = destinationTest(
        userIsLogged = false,
        expectedDestination = InitialView.Login
    )


    // Create a common test function for the two tests
    private fun destinationTest(
        userIsLogged: Boolean,
        expectedDestination: InitialView
    ) = runTest {
        val initialUseCase = InitialViewUseCase(
            preferenceStoreRepository = FakePreferenceStoreRepository(
                if (userIsLogged) InitialView.Main else InitialView.Login
            ),
            dispatcher = coroutineRule.testDispatcher
        )

        val viewModel = LauncherViewModel(
            initialViewUseCase = initialUseCase
        )

        val result = viewModel.launchDestination.first()
        assertThat(result).isEqualTo(expectedDestination)
    }

    private class FakePreferenceStoreRepository(initialView: InitialView) : PreferenceStoreRepository {
        override suspend fun cleanUserPreferences() {}

        override suspend fun updateInitialView(initialView: InitialView) {}

        override val initialView: Flow<InitialView> = flow { emit(initialView) }
        override suspend fun updateIsPagingFixed(isPagingFixed: Boolean) {}

        override val isPagingFixed: Flow<Boolean> = flow { emit(false) }
    }


}