package com.astar.downloadfiles

sealed class DownloadState {
    object Download: DownloadState()
    data class Error(val message: String): DownloadState()
    data class Finish(val filename: String): DownloadState()
}