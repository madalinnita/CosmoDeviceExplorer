package com.nitaioanmadalin.cosmodeviceexplorer.ui.cosmodevices

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.coroutine.CoroutineDispatchersProvider
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.log.LogProvider
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.network.AppResult
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.network.ConnectivityUtils
import com.nitaioanmadalin.cosmodeviceexplorer.domain.usecase.getdevices.GetCosmoDevicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CosmoDevicesViewModel @Inject constructor(
    private val getRepositoriesUseCase: GetCosmoDevicesUseCase,
    private val connectivityUtils: ConnectivityUtils,
    private val dispatchers: CoroutineDispatchersProvider,
    private val logProvider: LogProvider
): ViewModel() {

    private val _state: MutableStateFlow<CosmoDevicesScreenState> = MutableStateFlow(
        CosmoDevicesScreenState.Loading())
    val state: StateFlow<CosmoDevicesScreenState> = _state

    fun getData(context: Context) {
        if (connectivityUtils.isConnectionAvailable(context)) {
            viewModelScope.launch(dispatchers.io()) {
                // Delay applied in order to see properly the Loading screen for Assessment purposes
                delay(2000)
                getRepositoriesUseCase.getCosmoDevices().onEach {
                    withContext(dispatchers.main()) {
                        _state.value = when (it) {
                            is AppResult.Error -> {
                                logProvider.logError(TAG, "Error - ${it.message}")
                                CosmoDevicesScreenState.Error(it.exception)
                            }

                            is AppResult.Loading -> {
                                logProvider.logDebug(TAG, "Loading - ${it.daoData}")
                                CosmoDevicesScreenState.Loading(it.daoData)
                            }

                            is AppResult.Success -> {
                                logProvider.logDebug(TAG, "Success - ${it.successData}")
                                CosmoDevicesScreenState.Success(it.successData)
                            }
                        }
                    }
                }.launchIn(this)
            }
        } else {
            viewModelScope.launch {
                _state.value = CosmoDevicesScreenState.Error(
                    Throwable("Internet connection is not available"),
                    isInternetAvailable = false
                )
            }
        }
    }

    companion object{
        private const val TAG = "GithubViewModel"
    }
}