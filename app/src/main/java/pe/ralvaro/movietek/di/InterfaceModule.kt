package pe.ralvaro.movietek.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.ralvaro.movietek.data.network.FakeApiLogin
import pe.ralvaro.movietek.data.network.FakeServerSource
import pe.ralvaro.movietek.data.preferences.ProtoDataStorage
import pe.ralvaro.movietek.data.preferences.ProtoDataStorageImpl
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepository
import pe.ralvaro.movietek.data.repositories.PreferenceStoreRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class InterfaceModule {

    @Singleton
    @Binds
    abstract fun bindProtoPreferenceDataStore(
        protoPreferenceDataStorageImpl: ProtoDataStorageImpl
    ): ProtoDataStorage

    @Singleton
    @Binds
    abstract fun bindPreferenceStoreRepository(
        preferenceStoreRepositoryImpl: PreferenceStoreRepositoryImpl
    ): PreferenceStoreRepository

    @Binds
    abstract fun bindFakeApiImpl(
        fakeServerSource: FakeServerSource
    ): FakeApiLogin

}