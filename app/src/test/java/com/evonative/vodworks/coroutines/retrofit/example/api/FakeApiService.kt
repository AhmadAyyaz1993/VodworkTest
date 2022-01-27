package com.evonative.vodworks.coroutines.retrofit.example.api

import com.evonative.vodworks.coroutines.retrofit.example.JsonProvider
import com.evonative.vodworks.test.data.model.ItemData
import com.evonative.vodworks.test.data.network.ApiServices
import java.lang.Exception
import javax.inject.Inject

class FakeApiService @Inject constructor() : ApiServices {

    var failUserApi: Boolean = false
    var wrongResponse: Boolean = false

    override suspend fun getItemData(): List<ItemData> {
        if (failUserApi) throw Exception("Api failed")
        val fakeResponse: List<ItemData> = JsonProvider.objectFromJsonFileWithType(FAKE_RESPONSE)

        if (wrongResponse)  {
            return ArrayList<ItemData>();
        }

        return fakeResponse
    }

    companion object {
        private const val FAKE_RESPONSE = "/json/response.json"
        private const val WRONG_RESPONSE = "/json/response.json"
    }
}