package pe.ralvaro.movietek.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import pe.ralvaro.movietek.data.database.MovieEntity
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val rmSource: RemoteMediatorSourceRepository
) : RemoteMediator<Int, MovieEntity>() {

    companion object {
        const val PAGE_SIZE = 20
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val pageToDownload = rmSource.getPageToDownload()

            val loadKey = when (loadType) {
                REFRESH -> 1
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else pageToDownload
                }
            }

            val isLastPage =
                rmSource.downloadMoreDataAndReturnIsLast(pageNumber = loadKey) { downloadedData ->
                    rmSource.executeSave(
                        isRefreshCase = loadType == REFRESH,
                        listToSave = downloadedData
                    )
                }
            //Timber.e("La cantidad de elementos es -> ${movieEntity.size}")

            MediatorResult.Success(endOfPaginationReached = isLastPage)
        } catch (e: Exception) {
            Timber.e(e)
            val exception = Exception("Error in connection")
            MediatorResult.Error(exception)
        }
    }

}
