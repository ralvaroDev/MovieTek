package pe.ralvaro.movietek.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(

) : ViewModel() {

    // Emits the first activity to load
    val launchDestination = flow<InitialView> {
        emit(InitialView.Main)
    }

}

// Represents the possible activities to start the app
sealed interface InitialView {
    object Login : InitialView
    object Main : InitialView
}