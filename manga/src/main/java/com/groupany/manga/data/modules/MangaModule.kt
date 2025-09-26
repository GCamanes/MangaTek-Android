package com.groupany.manga.data.modules

import android.content.Context
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.groupany.manga.data.database.MangaDatabase
import com.groupany.manga.data.datasources.CoverDao
import com.groupany.manga.data.datasources.FavoriteDao
import com.groupany.manga.data.datasources.MangaRemoteDataSource
import com.groupany.manga.data.datasources.MangaRemoteDataSourceImpl
import com.groupany.manga.data.repositories.MangaRepositoryImpl
import com.groupany.manga.domain.repositories.MangaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MangaModule {
    @Provides
    @Singleton
    fun provideMangaRemoteDatasource(): MangaRemoteDataSource {
        return MangaRemoteDataSourceImpl(Firebase.firestore)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MangaDatabase =
        Room.databaseBuilder(
            context,
            MangaDatabase::class.java,
            "manga.db"
        )
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideCoverDao(db: MangaDatabase): CoverDao = db.coverDao()

    @Provides
    fun provideFavoriteDao(db: MangaDatabase): FavoriteDao = db.favoriteDao()

    @Provides
    @Singleton
    fun provideMangaRepository(
        remoteDatasource: MangaRemoteDataSource,
        coverDao: CoverDao,
        favoriteDao: FavoriteDao,
    ): MangaRepository {
        return MangaRepositoryImpl(
            remoteDatasource,
            coverDao = coverDao,
            favoriteDao = favoriteDao,
        )
    }
}