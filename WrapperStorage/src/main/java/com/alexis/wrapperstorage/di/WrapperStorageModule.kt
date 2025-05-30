package com.alexis.wrapperstorage.di

import android.content.Context
import com.alexis.wrapperstorage.core.factory.StorageFactory
import com.alexis.wrapperstorage.core.factory.StorageManagerFactory
import com.alexis.wrapperstorage.core.manager.IStorageManager
import com.alexis.wrapperstorage.core.model.enums.StorageTypeEnum
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WrapperStorageModule {

    private var storageTypeEnum: StorageTypeEnum = StorageTypeEnum.DATA_STORE
    private var storageName: String = "WRAPPER_STORAGE"

    @Provides
    @Singleton
    fun provideStorage(@ApplicationContext context: Context): IStorageManager {
        return StorageFactory().createStorage(context, storageTypeEnum, storageName)
    }

    @Provides
    @Singleton
    fun provideStorageManagerFactory(@ApplicationContext context: Context): StorageManagerFactory =
        StorageManagerFactory(context)

}