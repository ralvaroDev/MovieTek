package pe.ralvaro.movietek.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pe.ralvaro.movietek.data.database.MovieDao
import pe.ralvaro.movietek.data.database.MovieDatabase
import javax.inject.Singleton


private const val NAME_TABLE_MAIN = "main_table"

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        val builder = Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            NAME_TABLE_MAIN
        )
            .fallbackToDestructiveMigration()
        return builder.build()
    }

    @Provides
    fun provideDao(db: MovieDatabase) : MovieDao {
        return db.movieDao()
    }

}