package com.alexis.wrapperstorage.data.local.datastore

import android.content.Context
import android.content.SharedPreferences
import com.alexis.wrapperstorage.core.model.Serializer
import com.alexis.wrapperstorage.core.repository.IStorageRepository
import com.alexis.wrapperstorage.core.util.GsonSerializer
import com.alexis.wrapperstorage.presentation.model.StorageKey

class StorageDataStore(
    private val context: Context,
    private val storageName: String
) : IStorageRepository {

    private val serializer: Serializer = GsonSerializer()
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(storageName, Context.MODE_PRIVATE)
    }

    override suspend fun <T> put(key: StorageKey<T>, value: T) {
        val fullKey = key.fullKey()
        with(
            sharedPreferences.edit()) {
            when (value) {
                is String -> putString(fullKey, value)
                is Int -> putInt(fullKey, value)
                is Boolean -> putBoolean(fullKey, value)
                is Float -> putFloat(fullKey, value)
                is Long -> putLong(fullKey, value)
                else -> putString(fullKey, serializer.serialize(value))
            }
            apply()
        }
    }

    override suspend fun <T> get(key: StorageKey<T>, defaultValue: T): T {
        val fullKey = key.fullKey()
        return when (defaultValue) {
            is String -> sharedPreferences.getString(fullKey, defaultValue) as T
            is Int -> sharedPreferences.getInt(fullKey, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(fullKey, defaultValue) as T
            is Float -> sharedPreferences.getFloat(fullKey, defaultValue) as T
            is Long -> sharedPreferences.getLong(fullKey, defaultValue) as T
            else -> {
                val json = sharedPreferences.getString(fullKey, null)
                if (json != null) {
                    serializer.deserialize(json, defaultValue!!::class.java)
                } else {
                    defaultValue
                }
            }
        }
    }

    override suspend fun <T> remove(key: StorageKey<T>) {
        sharedPreferences.edit().remove(key.fullKey()).apply()
    }

}