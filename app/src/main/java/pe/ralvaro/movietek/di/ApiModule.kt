package pe.ralvaro.movietek.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import pe.ralvaro.movietek.BuildConfig
import pe.ralvaro.movietek.data.remote.MovieApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.TOKEN_TMDB}")
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .build()
                chain.proceed(request)
            }
            .callTimeout(timeout = 20L, TimeUnit.SECONDS)
            .build()


    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_MOVIE_URL)
        .client(okHttpClient)
        .build()


    @Singleton
    @Provides
    fun provideApiServices(
        retrofit: Retrofit
    ): MovieApiService = retrofit.create(MovieApiService::class.java)

}