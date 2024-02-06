package com.nitaioanmadalin.cosmodeviceexplorer.core.utils.coroutine

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchersProvider {
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
    fun computation(): CoroutineDispatcher
}