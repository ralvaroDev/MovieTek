package pe.ralvaro.movietek.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import pe.ralvaro.movietek.domain.launcher.InitialViewUseCase
import pe.ralvaro.movietek.utils.successOr
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    initialViewUseCase: InitialViewUseCase
) : ViewModel() {

    // Emits the first activity to load
    val launchDestination = initialViewUseCase(Unit).map {
        it.successOr(InitialView.Login)
    }

}

// Represents the possible activities to start the app
@Serializable
sealed interface InitialView {
    @Serializable
    object Login : InitialView
    @Serializable
    object Main : InitialView
}