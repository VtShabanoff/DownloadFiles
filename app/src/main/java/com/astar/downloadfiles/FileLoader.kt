package com.astar.downloadfiles

import android.content.Context
import java.io.File
import java.io.IOException

interface FileLoader {

    suspend fun saveFile(url: String, path: String, filename: String): Boolean

    class Base(
        private val context: Context,
        private val api: Api
    ) : FileLoader {
        override suspend fun saveFile(url: String, path: String, filename: String): Boolean {
            val file = File(context.getExternalFilesDir(path), filename)
            return try {
                file.outputStream().use { outStream ->
                    api.file(url).byteStream().use { inStream ->
                        inStream.copyTo(outStream)
                    }
                }
                true
            } catch (ioe: IOException) {
                file.delete()
                false
            }
        }
    }
}