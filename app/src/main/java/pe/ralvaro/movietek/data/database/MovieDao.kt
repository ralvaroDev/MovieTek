package pe.ralvaro.movietek.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie_table")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM movie_table")
    fun quantityData(): Flow<Int>

}