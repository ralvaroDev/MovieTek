package pe.ralvaro.movietek.data.preferences

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import pe.ralvaro.movietek.ui.InitialView
import javax.inject.Inject

interface ProtoDataStorage {
    suspend fun cleanUserPreferences()
    suspend fun updateInitialView(initialView: InitialView)
    suspend fun updateIsPagingFixed(isPagingFixed: Boolean)
    val userPreferences: Flow<ProtoPreferenceModel>
}

/**
 * Create this additional quasi-repo in cases that we have objects stored,
 * so here we make the first mapping and expose a granular data
 */
class ProtoDataStorageImpl @Inject constructor(
    private val dataStore: DataStore<ProtoPreferenceModel>
): ProtoDataStorage {

    override suspend fun cleanUserPreferences() {
        dataStore.updateData { ProtoPreferenceModel() }
    }

    override suspend fun updateInitialView(initialView: InitialView) {
        dataStore.updateData {
            it.copy(initialView = initialView)
        }
    }

    override suspend fun updateIsPagingFixed(isPagingFixed: Boolean) {
        dataStore.updateData {
            it.copy(isPagingFixed = isPagingFixed)
        }
    }

    override val userPreferences: Flow<ProtoPreferenceModel> = dataStore.data


}