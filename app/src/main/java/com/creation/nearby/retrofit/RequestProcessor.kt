package com.creation.nearby.retrofit

interface RequestProcessor<T> {

    suspend fun sendRequest(retrofitApi: RetrofitInterface): T

    fun onResponse(res: T)

    fun onException(message: String?)

}
