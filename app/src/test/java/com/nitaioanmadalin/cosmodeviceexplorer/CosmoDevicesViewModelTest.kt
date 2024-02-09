package com.nitaioanmadalin.cosmodeviceexplorer

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.coroutine.CoroutineDispatchersProvider
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.log.LogProvider
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.network.AppResult
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.network.ConnectivityUtils
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.CosmoDevice
import com.nitaioanmadalin.cosmodeviceexplorer.domain.usecase.getdevices.GetCosmoDevicesUseCase
import com.nitaioanmadalin.cosmodeviceexplorer.presentation.cosmodevices.CosmoDevicesScreenState
import com.nitaioanmadalin.cosmodeviceexplorer.presentation.cosmodevices.CosmoDevicesViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CosmoDevicesViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
    private val dispatchers = object : CoroutineDispatchersProvider {
        override fun io(): CoroutineDispatcher = testDispatcher
        override fun main(): CoroutineDispatcher = testDispatcher
        override fun computation(): CoroutineDispatcher = testDispatcher
    }

    private val scope = TestScope(testDispatcher)

    @MockK(relaxed = true)
    private lateinit var getCosmoDevicesUseCase: GetCosmoDevicesUseCase

    @MockK(relaxed = true)
    private lateinit var connectivityUtils: ConnectivityUtils

    @MockK(relaxed = true)
    private lateinit var mockContext: Context

    @MockK(relaxed = true)
    private lateinit var logProvider: LogProvider

    private lateinit var viewModel: CosmoDevicesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getData emits Success state when data is successfully retrieved`() =
        scope.runTest {
            val fakeData = listOf(
                CosmoDevice(
                    macAddress = "MockMacAddress",
                    model = "MockModel"
                )
            )
            coEvery { getCosmoDevicesUseCase.getCosmoDevices() } returns flowOf(
                AppResult.Success(
                    fakeData
                )
            )
            every { connectivityUtils.isConnectionAvailable(any()) } returns true

            val viewStateList = mutableListOf<CosmoDevicesScreenState>()
            val job = launch {
                viewModel = getViewModel()
                viewModel.state.toList(viewStateList)
            }

            viewModel.getData(mockContext)

            advanceTimeBy(2500) // To account for the hardcoded delay
            val state = viewStateList.last()
            assertTrue(state is CosmoDevicesScreenState.Success && state.repositories == fakeData)

            job.cancel()
        }

    @Test
    fun `getData emits Error state when an error occurs`() = scope.runTest {
        val exception = Exception("Error")
        coEvery { getCosmoDevicesUseCase.getCosmoDevices() } returns flowOf(
            AppResult.Error(
                exception
            )
        )
        every { connectivityUtils.isConnectionAvailable(any()) } returns true

        val viewStateList = mutableListOf<CosmoDevicesScreenState>()
        val job = launch {
            viewModel = getViewModel()
            viewModel.state.toList(viewStateList)
        }

        viewModel.getData(mockContext)

        advanceTimeBy(2500) // To account for the hardcoded delay
        val state = viewStateList.last()
        assertTrue(state is CosmoDevicesScreenState.Error && state.isInternetAvailable)

        job.cancel()
    }

    @Test
    fun `getData emits Error state when internet is not available`() =
        scope.runTest {
            every { connectivityUtils.isConnectionAvailable(any()) } returns false

            val viewStateList = mutableListOf<CosmoDevicesScreenState>()
            val job = launch {
                viewModel = getViewModel()
                viewModel.state.toList(viewStateList)
            }

            viewModel.getData(mockContext)

            advanceTimeBy(2500) // To account for the hardcoded delay
            val state = viewStateList.last()
            assertTrue(state is CosmoDevicesScreenState.Error && !state.isInternetAvailable)

            job.cancel()
        }

    private fun getViewModel() =
        CosmoDevicesViewModel(getCosmoDevicesUseCase, connectivityUtils, dispatchers, logProvider)

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}