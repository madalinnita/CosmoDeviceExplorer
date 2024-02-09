package com.nitaioanmadalin.cosmodeviceexplorer.domain.bluetooth

sealed interface ConnectionResult {
    object ConnectionEstablished: ConnectionResult
    data class Error(val message: String): ConnectionResult
}
