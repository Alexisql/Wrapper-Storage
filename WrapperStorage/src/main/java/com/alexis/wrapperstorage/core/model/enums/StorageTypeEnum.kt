package com.alexis.wrapperstorage.core.model.enums

/**
 * Enum que representa los tipos de almacenamiento soportados por la librería.
 *
 * Permite seleccionar entre diferentes mecanismos de persistencia de datos,
 * como SharedPreferences o DataStore.
 */
enum class StorageTypeEnum {
    /** Almacenamiento basado en SharedPreferences. */
    SHARED_PREFERENCES,

    /** Almacenamiento basado en DataStore. */
    DATA_STORE
}
