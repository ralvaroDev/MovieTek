package pe.ralvaro.movietek.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.ralvaro.movietek.data.preferences.ProtoDataStorage
import pe.ralvaro.movietek.data.preferences.ProtoDataStorageImpl
import pe.ralvaro.movietek.data.remote.FakeApiLogin
import pe.ralvaro.movietek.data.remote.FakeServerSource
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepository
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepositoryImpl
import pe.ralvaro.movietek.data.repositories.RemoteMediatorSourceRepository
import pe.ralvaro.movietek.data.repositories.RemoteMediatorSourceRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class InterfaceModule {

    @Binds
    abstract fun bindProtoPreferenceDataStore(
        protoDataStorageImpl: ProtoDataStorageImpl
    ): ProtoDataStorage

    @Binds
    abstract fun bindPreferenceStoreRepository(
        preferenceStoreRepositoryImpl: PreferenceStoreRepositoryImpl
    ): PreferenceStoreRepository

    @Binds
    abstract fun bindFakeApiImpl(
        fakeServerSource: FakeServerSource
    ): FakeApiLogin

    @Binds
    abstract fun bindRemoteMediatorSourceRepository(
        remoteMediatorSourceRepositoryImpl: RemoteMediatorSourceRepositoryImpl
    ): RemoteMediatorSourceRepository

}