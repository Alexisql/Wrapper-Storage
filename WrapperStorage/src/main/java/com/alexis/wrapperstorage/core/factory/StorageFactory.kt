package com.alexis.wrapperstorage.core.factory

import android.content.Context
import com.alexis.wrapperstorage.core.manager.IStorageManager
import com.alexis.wrapperstorage.core.model.ISerializer
import com.alexis.wrapperstorage.core.model.enums.StorageTypeEnum
import com.alexis.wrapperstorage.data.local.datastore.StorageDataStore
import com.alexis.wrapperstorage.data.local.sharedpreferences.StorageSharedPreferences

/**
 * Fábrica para crear instancias de [IStorageManager] según el tipo de almacenamiento especificado.
 *
 * Utiliza el patrón Factory para abstraer la creación de diferentes implementaciones de almacenamiento,
 * como SharedPreferences o DataStore.
 */
internal class StorageFactory {
    /**
     * Crea una instancia de [IStorageManager] basada en el [StorageTypeEnum] proporcionado.
     *
     * @param context Contexto de la aplicación.
     * @param storageName Nombre del almacenamiento.
     * @param storageType Tipo de almacenamiento a utilizar.
     * @param serializer Serializador para la conversión de objetos.
     * @return Instancia de [IStorageManager] correspondiente al tipo solicitado.
     */
    companion object {
        fun createStorage(
            context: Context,
            storageName: String,
            storageType: StorageTypeEnum,
            serializer: ISerializer
        ): IStorageManager {
            return when (storageType) {
                StorageTypeEnum.SHARED_PREFERENCES -> StorageSharedPreferences(
                    context,
                    storageName,
                    serializer
                )

                StorageTypeEnum.DATA_STORE -> StorageDataStore(
                    context,
                    storageName,
                    serializer
                )
            }
        }
    }
}