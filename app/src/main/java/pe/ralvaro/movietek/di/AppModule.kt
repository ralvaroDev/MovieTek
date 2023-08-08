package pe.ralvaro.movietek.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.ralvaro.movietek.data.network.FakeApiLogin
import pe.ralvaro.movietek.data.network.FakeServerSource
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideFakeServer() = FakeServerSource

}