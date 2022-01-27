package com.evonative.vodworks.coroutines.retrofit.example.hilt

import com.evonative.vodworks.coroutines.retrofit.example.api.FakeApiService
import com.evonative.vodworks.test.data.network.ApiServices
import com.evonative.vodworks.test.di.ApiServiceModule
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [ApiServiceModule::class])
abstract class FakeApiServiceModule {

    @Binds
    @Singleton
    abstract fun providesApiServices(fakeApiService: FakeApiService): ApiServices
}