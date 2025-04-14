package com.groupany.mangatek.core.data.modules

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.groupany.mangatek.core.data.datasources.MangaLocalDataSource
import com.groupany.mangatek.core.data.datasources.MangaLocalDataSourceImpl
import com.groupany.mangatek.core.data.datasources.MangaRemoteDataSource
import com.groupany.mangatek.core.data.datasources.MangaRemoteDataSourceImpl
import com.groupany.mangatek.core.data.repositories.MangaRepositoryImpl
import com.groupany.mangatek.core.domain.repositories.MangaRepository
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