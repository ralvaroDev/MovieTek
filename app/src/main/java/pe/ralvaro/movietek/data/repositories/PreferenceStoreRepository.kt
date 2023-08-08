package pe.ralvaro.movietek.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.ralvaro.movietek.data.preferences.ProtoDataStorage
import pe.ralvaro.movietek.ui.InitialView
import javax.inject.Inject

/**
 * Single access point to the user preferences data.
 */
interface PreferenceStoreRepository {
    suspend fun cleanUserPreferences()

    suspend fun updateInitialView(initialView: InitialView)
    val initialView: Flow<InitialView>
}

/**
 * Single access point to the user preferences data implementation.
 */
class PreferenceStoreRepositoryImpl @Inject constructor(
    private val protoDataStorage: ProtoDataStorage
): PreferenceStoreRepository {

    override suspend fun cleanUserPreferences() {
        protoDataStorage.cleanUserPreferences()
    }

    override suspend fun updateInitialView(initialView: InitialView) {
        protoDataStorage.updateInitialView(initialView)
    }

    override val initialView: Flow<InitialView> =
        protoDataStorage.userPreferences.map {
            it.initialView
        }
}