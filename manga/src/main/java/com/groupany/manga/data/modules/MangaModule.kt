package com.groupany.manga.data.modules

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.groupany.manga.data.datasources.MangaLocalDataSource
import com.groupany.manga.data.datasources.MangaLocalDataSourceImpl
import com.groupany.manga.data.datasources.MangaRemoteDataSource
import com.groupany.manga.data.datasources.MangaRemoteDataSourceImpl
import com.groupany.manga.data.repositories.MangaRepositoryImpl
import com.groupany.manga.domain.repositories.MangaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

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
    fun provideMangaLocalDatasource(@ApplicationContext appContext: Context): MangaLocalDataSource {
        return MangaLocalDataSourceImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideMangaRepository(
        remoteDatasource: MangaRemoteDataSource,
        localDatasource: MangaLocalDataSource,
    ): MangaRepository {
        return MangaRepositoryImpl(remoteDatasource, localDatasource)
    }
}