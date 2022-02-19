package com.astar.downloadfiles

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DownloadFileViewModel(
    private val loader: FileLoader,
    private val storage: SettingsStorage
) : ViewModel() {

    private val downloadState = MutableLiveData<DownloadState>()

    fun observeDownloadState(owner: LifecycleOwner, observer: Observer<DownloadState>) {
        downloadState.observe(owner, observer)
    }

    fun downloadFile(link: String) = viewModelScope.launch(Dispatchers.IO) {
        if (link.isNotEmpty() && !storage.isExists(link)) {
            val timestamp = System.currentTimeMillis()
            var filename = link.substring(link.lastIndexOf('/') + 1)
            filename = String.format("%d_%s", timestamp, filename)

            downloadState.postValue(DownloadState.Download)
            if (loader.saveFile(link, OUT_FOLDER, filename)) {
                storage.saveFile(link, filename)
                downloadState.postValue(DownloadState.Finish("Файл $filename загружен!"))
            } else {
                downloadState.postValue(DownloadState.Error("Ошибка при загрузке файла: $filename"))
            }
        } else {
            downloadState.postValue(DownloadState.Error("Файл уже был загружен ранее!"))
        }
    }

    private companion object {
        const val TAG = "DFViewModel"
        const val OUT_FOLDER = "out"
    }
}

