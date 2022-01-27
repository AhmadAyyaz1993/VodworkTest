package com.evonative.vodworks.test.data.network

import com.evonative.vodworks.test.data.model.ItemData
import retrofit2.Retrofit
import retrofit2.http.GET

interface ApiServices {

    @GET("/vodassets/showcase.json")
    suspend fun getItemData(): List<ItemData>

    companion object Factory {
        fun create(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)
    }
}