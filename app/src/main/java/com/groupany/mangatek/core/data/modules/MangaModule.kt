package com.groupany.mangatek.core.data.modules

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.groupany.mangatek.core.data.datasources.MangaRemoteDataSource
import com.groupany.mangatek.core.data.datasources.MangaRemoteDataSourceImpl
import com.groupany.mangatek.core.data.repositories.MangaRepositoryImpl
import com.groupany.mangatek.core.domain.repositories.MangaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.groupany.mangatek.features.auth.data.datasources.AuthDatasource
import com.groupany.mangatek.features.auth.data.datasources.AuthDatasourceImpl
import com.groupany.mangatek.features.auth.domain.repositories.AuthRepository
import com.groupany.mangatek.features.auth.data.repositories.AuthRepositoryImpl

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
    fun provideMangaRepository(datasource: MangaRemoteDataSource): MangaRepository {
        return MangaRepositoryImpl(datasource)
    }
}