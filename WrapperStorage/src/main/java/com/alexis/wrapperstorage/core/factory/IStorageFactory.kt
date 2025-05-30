package com.alexis.wrapperstorage.core.factory

import android.content.Context
import com.alexis.wrapperstorage.core.manager.IStorageManager
import com.alexis.wrapperstorage.core.model.ISerializer
import com.alexis.wrapperstorage.presentation.model.StorageConfig

fun interface IStorageFactory {
    fun createStorage(
        context: Context,
        config: StorageConfig,
        serializer: ISerializer
    ): IStorageManager
}