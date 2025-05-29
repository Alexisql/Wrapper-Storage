package com.alexis.wrapperstorage.core.factory

import android.content.Context
import com.alexis.wrapperstorage.core.model.enums.StorageTypeEnum
import com.alexis.wrapperstorage.core.manager.IStorageManager
import com.alexis.wrapperstorage.data.local.datastore.StorageDataStore
import com.alexis.wrapperstorage.data.local.sharedpreferences.StorageSharedPreferences

class StorageFactory : IStorageFactory {

    override fun createStorage(
        context: Context,
        storageTypeEnum: StorageTypeEnum,
        storageName: String
    ): IStorageManager {
        return when (storageTypeEnum) {
            StorageTypeEnum.SHARED_PREFERENCES -> StorageSharedPreferences(context, storageName)
            StorageTypeEnum.DATA_STORE -> StorageDataStore(context, storageName)
        }
    }
}