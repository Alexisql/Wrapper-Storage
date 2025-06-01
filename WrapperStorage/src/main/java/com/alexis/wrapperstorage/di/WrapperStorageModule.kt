package com.alexis.wrapperstorage.di

import android.content.Context
import com.alexis.wrapperstorage.core.model.ISerializer
import com.alexis.wrapperstorage.core.util.GsonSerializer
import com.alexis.wrapperstorage.data.local.datastore.StorageDataStore
import com.alexis.wrapperstorage.data.local.sharedpreferences.StorageSharedPreferences
import com.alexis.wrapperstorage.presentation.manager.StorageManager
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
annotation class SharedPreferences

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Datastore

/**
 * Módulo de Hilt para proveer dependencias relacionadas con el almacenamiento de datos.
 *
 * Expone implementaciones de [IStorageManager] utilizando tanto SharedPreferences como DataStore,
 * así como el serializador utilizado para la conversión de objetos.
 *
 * - Usa las anotaciones [SharedPreferences] y [Datastore] para distinguir las implementaciones.
 * - El nombre de almacenamiento por defecto es [STORAGE_NAME_DEFAULT].
 */
@Module
@InstallIn(SingletonComponent::class)
object WrapperStorageModule {

    /**
     * Provee una instancia singleton de [ISerializer] basada en Gson.
     */
    @Provides
    @Singleton
    fun provideSerializer(): ISerializer = GsonSerializer()

    /**
     * Provee una instancia singleton de [StorageManager] que utiliza DataStore como almacenamiento.
     *
     * @param context Contexto de la aplicación.
     * @param serializer Serializador para objetos personalizados.
     */
    @Datastore
    @Provides
    @Singleton
    fun provideDatastoreStorage(
        @ApplicationContext context: Context,
        serializer: ISerializer
    ): StorageManager = StorageManager(StorageDataStore(context, STORAGE_NAME_DEFAULT, serializer))

    /**
     * Provee una instancia singleton de [StorageManager] que utiliza SharedPreferences como almacenamiento.
     *
     * @param context Contexto de la aplicación.
     * @param serializer Serializador para objetos personalizados.
     */
    @SharedPreferences
    @Provides
    @Singleton
    fun provideSharedPreferencesStorage(
        @ApplicationContext context: Context,
        serializer: ISerializer
    ): StorageManager =
        StorageManager(StorageSharedPreferences(context, STORAGE_NAME_DEFAULT, serializer))

}