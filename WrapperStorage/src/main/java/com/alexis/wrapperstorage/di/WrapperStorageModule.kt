package com.alexis.wrapperstorage.di

import android.content.Context
import com.alexis.wrapperstorage.core.factory.StorageFactory
import com.alexis.wrapperstorage.core.manager.IStorageManager
import com.alexis.wrapperstorage.core.model.ISerializer
import com.alexis.wrapperstorage.core.model.enums.StorageTypeEnum
import com.alexis.wrapperstorage.core.util.GsonSerializer
import com.alexis.wrapperstorage.data.local.datastore.StorageDataStore
import com.alexis.wrapperstorage.data.local.sharedpreferences.StorageSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

const val STORAGE_NAME_DEFAULT = "STORAGE_WRAPPER"

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StorageTypeDatastore

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StorageTypeSharedPreferences

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SharedPreferences

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DatastoreStorage

/**
 * Módulo de Dagger Hilt para proveer las dependencias relacionadas con el almacenamiento de datos.
 *
 * Expone implementaciones de [IStorageManager] utilizando DataStore y SharedPreferences,
 * así como el serializador [ISerializer] basado en Gson. Utiliza calificadores personalizados
 * para distinguir entre los diferentes tipos de almacenamiento.
 */
@Module
@InstallIn(SingletonComponent::class)
object WrapperStorageModule {

    @Provides
    @Singleton
    @StorageTypeDatastore
    fun provideDatastore(): StorageTypeEnum {
        return StorageTypeEnum.DATA_STORE
    }

    @Provides
    @Singleton
    @StorageTypeSharedPreferences
    fun provideSharedPreferences(): StorageTypeEnum {
        return StorageTypeEnum.SHARED_PREFERENCES
    }

    @Provides
    @Singleton
    fun provideSerializer(): ISerializer = GsonSerializer()

    @Provides
    @Singleton
    fun provideStorage(
        @ApplicationContext context: Context,
        @StorageTypeSharedPreferences storageType: StorageTypeEnum,
        serializer: ISerializer
    ): IStorageManager =
        StorageFactory.createStorage(context, STORAGE_NAME_DEFAULT, storageType, serializer)

    @DatastoreStorage
    @Provides
    @Singleton
    fun provideDatastoreStorage(
        @ApplicationContext context: Context,
        serializer: ISerializer
    ): IStorageManager {
        return StorageDataStore(context,STORAGE_NAME_DEFAULT, serializer)
    }

    @SharedPreferences
    @Provides
    @Singleton
    fun provideSharedPreferencesStorage(
        @ApplicationContext context: Context,
        serializer: ISerializer
    ): IStorageManager =
        StorageSharedPreferences(context, STORAGE_NAME_DEFAULT, serializer)
}