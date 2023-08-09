package pe.ralvaro.movietek.util

import kotlinx.coroutines.flow.Flow
import org.mockito.kotlin.mock
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepository
import pe.ralvaro.movietek.ui.InitialView

class FakePreferenceStoreRepository: PreferenceStoreRepository {
    override suspend fun cleanUserPreferences() {
    }

    override suspend fun updateInitialView(initialView: InitialView) {
    }

    override val initialView: Flow<InitialView> = mock {  }
    override suspend fun updateIsPagingFixed(isPagingFixed: Boolean) {

    }

    override val isPagingFixed: Flow<Boolean> = mock {  }

}