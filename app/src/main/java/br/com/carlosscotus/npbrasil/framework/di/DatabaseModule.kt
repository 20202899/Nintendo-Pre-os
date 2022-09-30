package br.com.carlosscotus.npbrasil.framework.di

import android.content.Context
import androidx.room.Room
import br.com.carlosscotus.npbrasil.framework.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()

    @Provides
    fun provideFavoriteDao(appDatabase: AppDatabase) = appDatabase.favoriteDao()
}