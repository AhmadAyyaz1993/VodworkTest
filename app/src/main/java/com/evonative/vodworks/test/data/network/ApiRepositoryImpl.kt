package com.evonative.vodworks.test.data.network

import com.evonative.vodworks.test.data.model.ItemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiRepositoryImpl(private val services: ApiServices) : ApiRepository {

    override suspend fun getItemData(): Flow<List<ItemData>> = flow {
        emit(services.getItemData())
    }
}