package com.luqman.android.template.repositories.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.luqman.android.template.mvi.datastore.UserPreferences
import com.luqman.android.template.repositories.serializer.UserPreferenceSerializer
import com.luqman.android.template.repositories.service.user.UserApiService
import com.luqman.android.template.repositories.service.user.UserApiServiceImpl
import com.luqman.android.template.repositories.user.UserRepository
import com.luqman.android.template.repositories.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userDataStore: DataStore<UserPreferences>,
        userApiService: UserApiService
    ): UserRepository {
        return UserRepositoryImpl(
            dataStore = userDataStore,
            userApiService = userApiService
        )
    }

    @Provides
    @Singleton
    fun provideUserApiService(
        httpClient: HttpClient
    ): UserApiService {
        return UserApiServiceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideUserPreferenceSerializer(): UserPreferenceSerializer {
        return UserPreferenceSerializer()
    }

    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context,
        userPreferenceSerializer: UserPreferenceSerializer
    ): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = userPreferenceSerializer,
            produceFile = {
                context.dataStoreFile("user_preferences.pb")
            }
        )
    }

}