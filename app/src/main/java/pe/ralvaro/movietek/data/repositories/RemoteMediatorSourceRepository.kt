package pe.ralvaro.movietek.data.repositories

import androidx.room.withTransaction
import kotlinx.coroutines.flow.first
import pe.ralvaro.movietek.data.database.MovieDatabase
import pe.ralvaro.movietek.data.remote.MovieApiService
import pe.ralvaro.movietek.data.remote.models.NetUpcomingContainer
import pe.ralvaro.movietek.data.remote.models.toDatabaseModel
import javax.inject.Inject

interface RemoteMediatorSourceRepository {

    /**
     * Execute the save of the data
     * @param isRefreshCase true if the case is a refresh case, so proceed to delete all the data
     * @param listToSave the list to append
     */
    suspend fun executeSave(isRefreshCase: Boolean, listToSave: NetUpcomingContainer)

    /**
     * Get the next page to download
     * @return the page to download
     */
    suspend fun getPageToDownload(): Int

    /**
     * Execute request to get the next page data
     * @param pageNumber the page to download
     * @param block the block to execute after the request with the downloaded data in it
     * @return true if the page is the last else return false
     */
    suspend fun downloadMoreDataAndReturnIsLast(
        pageNumber: Int,
        block: suspend (NetUpcomingContainer) -> Unit
    ): Boolean
}

/**
 * Why a layer of abstraction?
 * Because we can change the implementation of the way of getting the nextPage to download
 * For the execution of the transaction, we can change the entity saved or something like that
 */
class RemoteMediatorSourceRepositoryImpl @Inject constructor(
    private val db: MovieDatabase,
    private val api: MovieApiService
) : RemoteMediatorSourceRepository {

    override suspend fun executeSave(
        isRefreshCase: Boolean, listToSave: NetUpcomingContainer
    ) {
        db.withTransaction {
            if (isRefreshCase)
                db.clearAllTables()
            db.movieDao().insertAll(movies = listToSave.toDatabaseModel())
        }
    }

    override suspend fun getPageToDownload(): Int {
        val quantityOfItems = db.movieDao().quantityData().first()
        return if (quantityOfItems % MovieRemoteMediator.PAGE_SIZE == 0) {
            quantityOfItems / MovieRemoteMediator.PAGE_SIZE + 1
        } else {
            (quantityOfItems / MovieRemoteMediator.PAGE_SIZE) + 2
        }
    }

    override suspend fun downloadMoreDataAndReturnIsLast(
        pageNumber: Int,
        block: suspend (NetUpcomingContainer) -> Unit
    ): Boolean {
        api.getUpcomingMovies(pageNumber).let {
            block(it)
            return it.movieList.isEmpty()
        }
    }

}