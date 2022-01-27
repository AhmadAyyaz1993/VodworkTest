package com.evonative.vodworks.coroutines.retrofit.example.repository

import android.os.Looper
import com.evonative.vodworks.coroutines.retrofit.example.api.FakeApiService
import com.evonative.vodworks.test.data.network.ApiRepository
import com.evonative.vodworks.test.data.network.ApiServices
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [25], application = HiltTestApplication::class,manifest = Config.NONE)
@ExperimentalCoroutinesApi
@LooperMode(LooperMode.Mode.PAUSED)
class RepositoryApiTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var apiRepository: ApiRepository

    @BindValue
    @JvmField
    val fakeApiService: FakeApiService = FakeApiService()

    @Inject
    lateinit var apiServices: ApiServices

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        hiltRule.inject()
    }

    @Test
    fun `test Movies data success`() = runBlockingTest {
        apiRepository.getItemData()
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        apiRepository.getItemData().collect {
            assertNotNull(it)
            assertTrue(it.size > 0 && it.size == 45)
        }

    }

    @Test
    fun `test Movies data api failure`() = runBlockingTest {
        fakeApiService.failUserApi = true
        apiRepository.getItemData()
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        apiRepository.getItemData().catch {
            assertTrue("Api failed".equals("Api failed"))
        }
    }

    @Test
    fun `test Movies wrong data`() = runBlockingTest {
        fakeApiService.wrongResponse = true
        apiRepository.getItemData()
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        apiRepository.getItemData().collect {
            assertTrue(it.size == 0)
        }

    }

}