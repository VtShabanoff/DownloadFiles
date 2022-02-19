package com.astar.downloadfiles

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Url

object NetworkClient {

    fun getApi(): Api {
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://github.com")
            .build()
        return retrofit.create()
    }
}

interface Api {

    @GET
    suspend fun file(@Url url: String): ResponseBody
}
