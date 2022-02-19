package com.astar.downloadfiles

import android.app.Application

class MainApp: Application() {

    lateinit var loader: FileLoader
    lateinit var storage: SettingsStorage

    override fun onCreate() {
        super.onCreate()
        loader = FileLoader.Base(this, NetworkClient.getApi())
        storage = SettingsStorage.Base(this)
    }
}