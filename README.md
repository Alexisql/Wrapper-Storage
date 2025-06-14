# Wrapper-Storage
<p align="left">
<img src="https://img.shields.io/badge/Android-%23FFFFFF?logo=android">
<img src="https://img.shields.io/badge/Kotlin-%23FFFFFF?logo=kotlin">
</p>
WrapperStorage es un módulo de almacenamiento clave-valor para Android, diseñado para facilitar la gestión de preferencias y datos persistentes de forma segura y desacoplada, utilizando inyección de dependencias con Hilt.
<br><br>
[Storage Test](https://github.com/Alexisql/TestWrapperStorage) Es una app donde implementamos Wrapper Storage, siguiendo la arquitectura recomenda por Android. 

### Configuración:
#### build.gradle (:app)
```
implementation 'com.github.Alexisql:Wrapper-Storage:1.1.7'
```
#### settings.gradle
```
repositories {
     ...
     maven {
       url = uri("https://jitpack.io")
     }
}

dependencyResolutionManagement {
     ...
     maven {
       url = uri("https://jitpack.io")
     }

}
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
###### Exception
Para controlar el guardado de preferencias manejamos la excepcion PutException(val key: String, val value: String) con el mensaje personalizado "Se presento un error al guardar la preferencia con clave $key y valor $value"

<hr>

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
###### Exception
Para controlar la obtencion de preferencias manejamos la excepcion GetException(val key: String) con el mensaje personalizado "Se presento un error al obtener la preferencia con clave $key"

<hr>

###### Eliminar datos
Para la funcion eliminar, necesitaremos nuestra key.
Esta es una funcion suspendida, por ende debemos ejecutarla dentro de una corrutina u otra funcion suspendida.
```
lifecycleScope.launch {
            storage.remove(key.toString())
        }
```
###### Exception
Para controlar la eliminacion de preferencias manejamos la excepcion RemoveException(val key: String) con el mensaje personalizado "Se presento un error al eliminar la preferencia con clave $key"

<hr>

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
###### Exception
Para controlar la eliminacion de preferencias manejamos la excepcion GetPreferencesException(val screen: String) con el mensaje personalizado "Se presento un error al obtener las preferencias de la pantalla $screen"












