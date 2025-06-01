package com.alexis.wrapperstorage.presentation.manager

import com.alexis.wrapperstorage.core.manager.IStorageManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementación de [IStorageManager] que actúa como fachada para el almacenamiento de datos.
 *
 * Esta clase delega todas las operaciones de almacenamiento a una instancia de [IStorageManager]
 * proporcionada por inyección de dependencias, permitiendo intercambiar fácilmente la implementación subyacente.
 *
 * @constructor Crea un StorageManager con la instancia de [IStorageManager] especificada.
 * @param storage Instancia de [IStorageManager] utilizada para las operaciones de almacenamiento.
 */
@Singleton
class StorageManager @Inject constructor(
    private val storage: IStorageManager
) : IStorageManager by storage