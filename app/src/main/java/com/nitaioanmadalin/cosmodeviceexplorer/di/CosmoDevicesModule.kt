package com.nitaioanmadalin.cosmodeviceexplorer.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.coroutine.CoroutineDispatchersProvider
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.coroutine.CoroutineDispatchersProviderImpl
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.network.ConnectivityUtils
import com.nitaioanmadalin.cosmodeviceexplorer.data.local.CosmoDevicesDatabase
import com.nitaioanmadalin.cosmodeviceexplorer.data.remote.api.CosmoDevicesApi
import com.nitaioanmadalin.cosmodeviceexplorer.data.repository.CosmoDevicesRepositoryImpl
import com.nitaioanmadalin.cosmodeviceexplorer.domain.repository.CosmoDevicesRepository
import com.nitaioanmadalin.cosmodeviceexplorer.domain.usecase.GetCosmoDevicesUseCase
import com.nitaioanmadalin.cosmodeviceexplorer.domain.usecase.GetCosmoDevicesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CosmoDevicesModule {

    @Provides
    @Singleton
    fun getGson(): Gson {
        return GsonBuilder().serializeNulls().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideCosmoDevicesUseCase(
        repository: CosmoDevicesRepository
    ): GetCosmoDevicesUseCase {
        return GetCosmoDevicesUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideCosmoDevicesRepository(
        db: CosmoDevicesDatabase,
        api: CosmoDevicesApi
    ): CosmoDevicesRepository {
        return CosmoDevicesRepositoryImpl(db.dao, api)
    }

    @Provides
    @Singleton
    fun provideCosmoDevicesDatabase(
        application: Application
    ): CosmoDevicesDatabase {
        return Room.databaseBuilder(
            application,
            CosmoDevicesDatabase::class.java,
            "cosmodevices_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesGithubApi(client: OkHttpClient): CosmoDevicesApi {
        return Retrofit
            .Builder()
            .baseUrl(CosmoDevicesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CosmoDevicesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideConnectivityUtils(): ConnectivityUtils {
        return ConnectivityUtils()
    }

    @Provides
    fun provideCoroutineDispatchersProvider(): CoroutineDispatchersProvider {
        return CoroutineDispatchersProviderImpl()
    }
}