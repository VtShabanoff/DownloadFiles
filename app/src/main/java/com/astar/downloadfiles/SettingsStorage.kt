package com.astar.downloadfiles

import android.content.Context

interface SettingsStorage {

    fun saveFile(key: String, filename: String)

    fun isExists(key: String): Boolean

    class Base(context: Context): SettingsStorage {

        private val preferences = context.getSharedPreferences(NAME_STORAGE, Context.MODE_PRIVATE)

        override fun saveFile(key: String, filename: String) {
            preferences.edit().putString(key, filename).apply()
        }

        override fun isExists(key: String): Boolean {
            return preferences.contains(key)
        }

        private companion object {
            const val NAME_STORAGE = "com.astar.downloadfiles.SettingsStorage"
        }
    }
}