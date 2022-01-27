package com.evonative.vodworks.test.data.network

import com.evonative.vodworks.test.data.model.ItemData
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

    suspend fun getItemData(): Flow<List<ItemData>>
}