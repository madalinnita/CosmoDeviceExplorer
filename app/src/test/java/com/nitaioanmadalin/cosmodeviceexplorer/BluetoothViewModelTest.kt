package com.nitaioanmadalin.cosmodeviceexplorer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nitaioanmadalin.cosmodeviceexplorer.domain.bluetooth.BluetoothController
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.BluetoothDeviceItem
import com.nitaioanmadalin.cosmodeviceexplorer.presentation.bluetooth.BluetoothUiState
import com.nitaioanmadalin.cosmodeviceexplorer.presentation.bluetooth.BluetoothViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BluetoothViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    private lateinit var bluetoothController: BluetoothController

    private lateinit var viewModel: BluetoothViewModel

    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val scope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        coEvery { bluetoothController.scannedDevices } returns MutableStateFlow(emptyList())
        coEvery { bluetoothController.pairedDevices } returns MutableStateFlow(emptyList())
    }

    @Test
    fun `when startScan is called then trigger startScanning on bluetoothController`() = runTest {
        getViewModel().startScan()
        verify(exactly = 1) {
            bluetoothController.startScanning()
        }
    }

    @Test
    fun `when stopScan is called then trigger stopScanning on bluetoothController`() = runTest {
        getViewModel().stopScan()
        verify(exactly = 1) {
            bluetoothController.stopScanning()
        }
    }

    @Test
    fun `when connectToDevice is called then trigger connectToDevice on bluetoothController with correct device`() =
        scope.runTest {
            val device = BluetoothDeviceItem(name = "MockName", macAddress = "MockMackAddress")

            val viewStateList = mutableListOf<BluetoothUiState>()
            val job = launch {
                viewModel = getViewModel()
                viewModel.state.toList(viewStateList)
            }

            viewModel.connectToDevice(device)

            verify(exactly = 1) {
                bluetoothController.connectToDevice(device)
            }

            val state = viewStateList.last()
            Assert.assertTrue(state.isConnecting)

            job.cancel()
        }

    @Test
    fun `when disconnectFromDevice is called then trigger closeConnection on bluetoothController and update state`() =
        scope.runTest {
            val viewStateList = mutableListOf<BluetoothUiState>()
            val job = launch {
                viewModel = getViewModel()
                viewModel.state.toList(viewStateList)
            }

            viewModel.disconnectFromDevice()

            verify(exactly = 1) {
                bluetoothController.closeConnection()
            }

            val state = viewStateList.last()
            Assert.assertFalse(state.isConnecting)
            Assert.assertFalse(state.isConnected)

            job.cancel()
        }

    @Test
    fun `when waitForIncomingConnections is called then trigger startBluetoothServer on bluetoothController and update state`() =
        scope.runTest {
            val viewStateList = mutableListOf<BluetoothUiState>()
            val job = launch {
                viewModel = getViewModel()
                viewModel.state.toList(viewStateList)
            }

            viewModel.waitForIncomingConnections()

            verify(exactly = 1) {
                bluetoothController.startBluetoothServer()
            }

            val state = viewStateList.last()
            Assert.assertTrue(state.isConnecting)

            job.cancel()
        }

    private fun getViewModel() =
        BluetoothViewModel(bluetoothController)

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}