package com.alexis.wrapperstorage.core.factory

import android.content.Context
import com.alexis.wrapperstorage.core.model.enums.StorageType
import com.alexis.wrapperstorage.core.repository.IStorageRepository
import com.alexis.wrapperstorage.data.local.datastore.StorageDataStore
import com.alexis.wrapperstorage.data.local.sharedpreferences.StorageSharedPreferences

class StorageFactory(
    private val context: Context
) {
    fun getStorage(
        type: StorageType,
        storageName: String
    ): IStorageRepository {
        return when (type) {
            StorageType.SHARED_PREFERENCES -> StorageSharedPreferences(context, storageName)
            StorageType.DATA_STORE -> StorageDataStore(context, storageName)
        }
    }
}