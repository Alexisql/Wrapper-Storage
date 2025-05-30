package com.alexis.wrapperstorage.di

import android.content.Context
import com.alexis.wrapperstorage.core.factory.StorageFactory
import com.alexis.wrapperstorage.core.manager.IStorageManager
import com.alexis.wrapperstorage.core.model.ISerializer
import com.alexis.wrapperstorage.core.model.enums.StorageTypeEnum
import com.alexis.wrapperstorage.core.util.GsonSerializer
import com.alexis.wrapperstorage.presentation.manager.StorageManagerFactory
import com.alexis.wrapperstorage.presentation.model.StorageConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WrapperStorageModule {

    private var storageConfig: StorageConfig = StorageConfig(
        storageName = "STORAGE_WRAPPER",
        storageType = StorageTypeEnum.DATA_STORE
    )

    @Provides
    @Singleton
    fun provideSerializer(): ISerializer {
        return GsonSerializer()
    }

    @Provides
    @Singleton
    fun provideStorage(
        @ApplicationContext context: Context,
        serializer: ISerializer
    ): IStorageManager {
        return StorageFactory().createStorage(context, storageConfig, serializer)
    }

    @Provides
    @Singleton
    fun provideStorageManagerFactory(
        @ApplicationContext context: Context,
        serializer: ISerializer
    ): StorageManagerFactory =
        StorageManagerFactory(context, serializer)

}