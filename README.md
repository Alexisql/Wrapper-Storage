# Wrapper-Storage
<p align="left">
<img src="https://img.shields.io/badge/Android-%23FFFFFF?logo=android">
<img src="https://img.shields.io/badge/Kotlin-%23FFFFFF?logo=kotlin">
</p>
WrapperStorage es un módulo de almacenamiento clave-valor para Android, diseñado para facilitar la gestión de preferencias y datos persistentes de forma segura y desacoplada, utilizando inyección de dependencias con Hilt.
<br><br>
[Storage Test](https://github.com/Alexisql/TestWrapperStorage) Es una app donde implementamos Wrapper Storage, siguiendo la arquitectura recomenda por Android. 

### Configuración:

###### Incluir dependencia gradle
```
implementation 'com.github.Alexisql:Wrapper-Storage:1.1.5'
```
### Implementación:

###### Crea una instacia de StorageManegar
```
val storage = StorageManager(
            StorageDataStore(
                context = this@MainActivity,
                storageName = "test_storage",
                serializer = GsonSerializer()
            )
        )
```
###### Crea tu key
```
val key = StorageKey<String>(
            name = "Test",
            screen = "Main",
            client = "Alexis")
```
###### Guardar datos
Para la funcion guardar, necesitaremos nuestra key y el valor a guardar.
Esta es una funcion suspendida, por ende debemos ejecutarla dentro de una corrutina u otra funcion suspendida.
```
lifecycleScope.launch {
            storage.put(key, "Value")
        }
```
###### Obtener datos
Para la funcion obtener, necesitaremos nuestra key y un valor por defecto en caso que no exista ningun dato con esa key.
Esta funcion nos retorna un Flow<T>.
```
lifecycleScope.launch {
            storage.get(key, "Default Value").collect {
                it
            }
        }
```
###### Eliminar datos
Para la funcion eliminar, necesitaremos nuestra key.
Esta es una funcion suspendida, por ende debemos ejecutarla dentro de una corrutina u otra funcion suspendida.
```
lifecycleScope.launch {
            storage.remove(key.toString())
        }
```
###### Obtener datos por pantalla
Para la funcion obtener datos por pantalla, necesitaremos la variable screen de nuestra key.
Esta funcion nos retorna un Flow<Map<String, *>>.
```
lifecycleScope.launch {
            storage.getPreferencesByScreen("Main").collect {
                it
            }
        }
```












