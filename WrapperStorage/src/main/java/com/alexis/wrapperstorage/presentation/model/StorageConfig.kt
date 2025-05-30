package com.alexis.wrapperstorage.presentation.model

import com.alexis.wrapperstorage.core.model.enums.StorageTypeEnum

data class StorageConfig(
    val storageName: String,
    val storageType: StorageTypeEnum
)
