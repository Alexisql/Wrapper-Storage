package com.alexis.wrapperstorage.di

import android.content.Context
import com.alexis.wrapperstorage.core.factory.StorageFactory
import com.alexis.wrapperstorage.core.manager.IStorageManager
import com.alexis.wrapperstorage.core.model.enums.StorageTypeEnum
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WrapperStorageModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class StorageName

    private var storageTypeEnum: StorageTypeEnum = StorageTypeEnum.SHARED_PREFERENCES
    private var storageName: String = "WRAPPER_STORAGE"

    fun setStorageType(storageTypeEnum: StorageTypeEnum) {
        this.storageTypeEnum = storageTypeEnum
    }

    fun setStorageName(storageName: String) {
        this.storageName = storageName
    }

    @Provides
    @Singleton
    @StorageName
    fun provideStorageName(): String {
        return storageName
    }

    @Provides
    @Singleton
    fun provideStorage(@ApplicationContext context: Context): IStorageManager {
        return StorageFactory().createStorage(context, storageTypeEnum, storageName)
    }

}