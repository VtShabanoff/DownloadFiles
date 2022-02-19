package com.astar.downloadfiles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DownloadFileViewModelFactory(
    private val loader: FileLoader,
    private val storage: SettingsStorage
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(DownloadFileViewModel::class.java)) {
            return DownloadFileViewModel(loader, storage) as T
        }
        throw IllegalArgumentException("ViewModel not found")
    }
}