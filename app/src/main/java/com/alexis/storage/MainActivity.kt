package com.alexis.storage

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.alexis.wrapperstorage.core.model.enums.StorageType
import com.alexis.wrapperstorage.presentation.manager.StorageManager
import com.alexis.wrapperstorage.presentation.model.StorageKey
import com.alexis.wrapperstorage.presentation.model.User
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val storageManager = StorageManager.getInstance(
                this@MainActivity,
                StorageType.SHARED_PREFERENCES,
                "WRAPPER_STORAGE"
            )
            val storageKey = StorageKey<String>("A", "MainA", "Alexis")
            val storageKey2 = StorageKey<User>("B", "MainB", "Alex")

            storageManager.put(storageKey, "Alexis")
            storageManager.put(storageKey2, User("Alexis", 25))

            val username = storageManager.get(storageKey, "null")
            val user = storageManager.get(storageKey2, User("null", 0))
            Log.e("****Storage****", "User Name: $username, User: $user")
        }
    }
}
