package com.alexis.wrapperstorage.presentation.manager

import com.alexis.wrapperstorage.core.manager.IStorageManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageManager @Inject constructor(
    private val storage: IStorageManager
) : IStorageManager by storage