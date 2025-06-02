package com.alexis.wrapperstorage.core.util

/**
 * Utilidad interna para manipular y filtrar claves de almacenamiento.
 *
 * Proporciona métodos para filtrar preferencias según el identificador de pantalla,
 * facilitando la obtención de datos agrupados por pantalla.
 */
internal object StorageKeyHelper {
    /**
     * Filtra las preferencias almacenadas para obtener solo aquellas asociadas a una pantalla específica.
     *
     * @param preferences Mapa de todas las preferencias almacenadas, donde la clave es el nombre de la preferencia.
     * @param screen Identificador de la pantalla para filtrar las preferencias.
     * @return Un mapa con las preferencias cuyo nombre contiene el identificador de pantalla.
     */
    fun filterPreferencesByScreen(
        preferences: Map<String, *>,
        screen: String
    ): Map<String, *> {
        return preferences.filter { (key, _) -> key.contains(screen) }
    }
}