package pe.ralvaro.movietek.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.ralvaro.movietek.data.network.FakeApiLogin
import pe.ralvaro.movietek.data.network.FakeServerSource
import pe.ralvaro.movietek.data.network.MovieApiService
import pe.ralvaro.movietek.data.repositories.LocalMovieSource
import pe.ralvaro.movietek.data.repositories.MovieDataSource
import pe.ralvaro.movietek.data.repositories.RemoteMovieSource
import pe.ralvaro.movietek.utils.NetworkUtils
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideFakeServer() = FakeServerSource

    @Singleton
    @Provides
    @Named("remoteDataSource")
    fun provideRemoteDataSource(
        movieApiService: MovieApiService,
        networkUtils: NetworkUtils
    ): MovieDataSource {
        return RemoteMovieSource(movieApiService, networkUtils)
    }

    @Singleton
    @Provides
    @Named("localDataSource")
    fun provideLocalDataSource(): MovieDataSource {
        return LocalMovieSource()
    }



}