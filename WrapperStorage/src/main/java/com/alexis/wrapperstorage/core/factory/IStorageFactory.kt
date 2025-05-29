package com.alexis.wrapperstorage.core.factory

import android.content.Context
import com.alexis.wrapperstorage.core.manager.IStorageManager
import com.alexis.wrapperstorage.core.model.enums.StorageTypeEnum

fun interface IStorageFactory {
    fun createStorage(
        context: Context,
        storageTypeEnum: StorageTypeEnum,
        storageName: String
    ): IStorageManager
}