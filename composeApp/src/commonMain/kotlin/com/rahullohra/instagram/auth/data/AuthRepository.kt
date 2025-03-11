package com.rahullohra.instagram.auth.data

import com.rahullohra.instagram.auth.data.local.AuthStore
import com.rahullohra.instagram.auth.data.remote.AuthApiService
import com.rahullohra.instagram.auth.data.remote.LoginRequest
import com.rahullohra.instagram.core.BaseRepository
import com.rahullohra.instagram.models.auth.LoginResponse
import com.rahullohra.instagram.network.DataLayerResponse


class AuthRepository(
    private val authApiService: AuthApiService,
    private val authStorage: AuthStore,
): BaseRepository() {

//    suspend fun signup(username: String, password: String): DataLayerResponse<LoginResponse> {
//        val response = safeApiCall { authApiService.signup(username, password) }
//        when (response) {
//
//            is DataLayerResponse.Success -> {
//                authStorage.storeCredentials(
//                    UserAuth(
//                        response.data.accessToken,
//                        response.data.accessToken
//                    )
//                )
//            }
//
//            is DataLayerResponse.Error -> {}
//        }
//        return response
//    }

    suspend fun login(username: String, password: String): DataLayerResponse<LoginResponse> {
        try {

            val response = safeApiCall {
                authApiService.login(LoginRequest(username, password))
            }
            when (response) {
                is DataLayerResponse.Success -> {
                    authStorage.storeCredentials(
                        UserAuth(
                            response.data.accessToken,
                            response.data.accessToken
                        )
                    )
                }

                is DataLayerResponse.Error -> {
                    println("ApiResponse.Error")
                }
            }
            return response
        } catch (ex: Exception) {
            return DataLayerResponse.Error(ex.message ?: "Unknown error")
        }
    }


}
