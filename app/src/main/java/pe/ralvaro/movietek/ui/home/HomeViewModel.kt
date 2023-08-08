package pe.ralvaro.movietek.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepository
import pe.ralvaro.movietek.ui.InitialView
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferenceStoreRepository: PreferenceStoreRepository
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            preferenceStoreRepository.updateInitialView(InitialView.Login)
        }
    }

}