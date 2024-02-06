package com.nitaioanmadalin.cosmodeviceexplorer.data.remote.api

import com.nitaioanmadalin.cosmodeviceexplorer.data.remote.response.CosmoDevicesResponse
import retrofit2.http.GET

interface CosmoDevicesApi {
    @GET("/test/devices")
   suspend fun getDevices(): CosmoDevicesResponse

   companion object {
       val BASE_URL = "https://cosmo-api.develop-sr3snxi-x6u2x52ooksf4.de-2.platformsh.site/"
   }
}