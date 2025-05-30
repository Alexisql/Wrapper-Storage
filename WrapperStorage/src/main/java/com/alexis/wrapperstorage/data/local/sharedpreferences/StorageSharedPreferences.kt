package com.alexis.wrapperstorage.data.local.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.alexis.wrapperstorage.core.manager.IStorageManager
import com.alexis.wrapperstorage.core.model.ISerializer
import com.alexis.wrapperstorage.presentation.model.StorageKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StorageSharedPreferences(
    private val context: Context,
    private val storageName: String,
    private val serializer: ISerializer
) : IStorageManager {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(storageName, Context.MODE_PRIVATE)
    }

    override suspend fun <T> put(key: StorageKey<T>, value: T) {
        val fullKey = key.fullKey()
        sharedPreferences.edit {
            when (value) {
                is String -> putString(fullKey, value)
                is Int -> putInt(fullKey, value)
                is Boolean -> putBoolean(fullKey, value)
                is Float -> putFloat(fullKey, value)
                is Long -> putLong(fullKey, value)
                else -> putString(fullKey, serializer.serialize(value))
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: StorageKey<T>, defaultValue: T): Flow<T> {
        val fullKey = key.fullKey()
        val value: T = when (defaultValue) {
            is String -> sharedPreferences.getString(fullKey, defaultValue) as T
            is Int -> sharedPreferences.getInt(fullKey, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(fullKey, defaultValue) as T
            is Float -> sharedPreferences.getFloat(fullKey, defaultValue) as T
            is Long -> sharedPreferences.getLong(fullKey, defaultValue) as T
            else -> serializer.deserialize(sharedPreferences.getString(fullKey, null), defaultValue)
        }
        return MutableStateFlow(value).asStateFlow()
    }

    override suspend fun <T> remove(key: StorageKey<T>) {
        sharedPreferences.edit { remove(key.fullKey()) }
    }

}