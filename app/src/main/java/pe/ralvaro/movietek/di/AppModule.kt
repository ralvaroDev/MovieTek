package pe.ralvaro.movietek.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.ralvaro.movietek.data.database.MovieDao
import pe.ralvaro.movietek.data.database.MovieEntity
import pe.ralvaro.movietek.data.remote.FakeServerSource
import pe.ralvaro.movietek.data.repositories.MovieRemoteMediator
import pe.ralvaro.movietek.data.repositories.RemoteMediatorSourceRepository
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideFakeServer() = FakeServerSource

    @Provides
    @Singleton
    fun providePagerMediator(
        rmSourceRepository: RemoteMediatorSourceRepository,
        dao: MovieDao
    ): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieRemoteMediator.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = MovieRemoteMediator(
                rmSource = rmSourceRepository
            ),
            pagingSourceFactory = { dao.pagingSource() }
        )
    }


}