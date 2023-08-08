package pe.ralvaro.movietek.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import pe.ralvaro.movietek.data.preferences.ProtoPreferenceModel
import pe.ralvaro.movietek.data.preferences.ProtoStoreSerializer
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PreferenceModule {

    @Singleton
    @Provides
    fun provideProtoDataStore(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope,
        protoStoreSerializer: ProtoStoreSerializer
        ): DataStore<ProtoPreferenceModel> = DataStoreFactory.create(
            serializer = protoStoreSerializer,
            corruptionHandler = null,
            scope = coroutineScope,
            produceFile = { context.dataStoreFile(PREFS_NAME) }
        )

    @Singleton
    @Provides
    fun provideSerializer(): ProtoStoreSerializer = ProtoStoreSerializer

}

private const val PREFS_NAME = "proto_store"