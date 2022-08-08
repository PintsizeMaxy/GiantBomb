package com.max.giantbomb

import android.content.Context
import androidx.room.Room
import com.max.giantbomb.cache.GameDao
import com.max.giantbomb.cache.GameDatabase
import com.max.giantbomb.remote.GiantBombClient
import com.max.giantbomb.remote.GiantBombRepository
import com.max.giantbomb.remote.GiantBombRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class GiantBombModule {
    companion object {
        private const val BASE_URL = " https://www.giantbomb.com/api/"

        @Provides
        @Singleton
        fun providesOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().connectTimeout(30_000, TimeUnit.SECONDS).build()
        }

        @Provides
        @Singleton
        fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build()
        }

        @Provides
        @Singleton
        fun provideGiantBombClient(retrofit: Retrofit): GiantBombClient {
            return retrofit.create(GiantBombClient::class.java)
        }

        @Provides
        @Singleton
        fun provideGiantBombRepository(
            giantBombClient: GiantBombClient,
            giantBombDao: GameDao
        ): GiantBombRepository {
            return GiantBombRepositoryImpl(giantBombClient, giantBombDao)
        }

        @Provides
        @Singleton
        fun provideGameDao(gameDatabase: GameDatabase): GameDao{
            return gameDatabase.gameDao()
        }

        @Provides
        fun provideGameDatabase(@ApplicationContext context: Context): GameDatabase {
            return Room.databaseBuilder(context, GameDatabase::class.java, "Gamedb").build()
        }
    }
}