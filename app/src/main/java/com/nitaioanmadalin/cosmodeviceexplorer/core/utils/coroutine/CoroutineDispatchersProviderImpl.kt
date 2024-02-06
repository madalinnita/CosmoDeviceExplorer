package com.nitaioanmadalin.cosmodeviceexplorer.core.utils.coroutine

import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.coroutine.CoroutineDispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatchersProviderImpl: CoroutineDispatchersProvider {
    override fun io(): CoroutineDispatcher = Dispatchers.IO

    override fun main(): CoroutineDispatcher = Dispatchers.Main

    override fun computation(): CoroutineDispatcher = Dispatchers.Default
}