package com.rahullohra.instagram.feed.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rahullohra.instagram.feed.data.FeedPagingSource
import kotlinx.coroutines.flow.MutableStateFlow

class FeedViewmodel(val feedPagingSource: FeedPagingSource) : ViewModel() {

    companion object {
        const val KEY = "FeedViewmodel"
    }

    val feedPagingFlow = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { feedPagingSource }
    ).flow.cachedIn(viewModelScope)

    val username: MutableStateFlow<UsernameUiState> = MutableStateFlow(UsernameUiState.Empty)
    val password: MutableStateFlow<PasswordUiState> = MutableStateFlow(PasswordUiState.Empty)
    val toast: MutableStateFlow<ToastUiState> = MutableStateFlow(ToastUiState.Empty)
    val loginState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Empty)

    fun onLogin(username: String, password: String) {
        if (username.isEmpty()) {
            loginState.value = LoginUiState.Fail("Username cannot be empty")
            return
        }

        if (password.isEmpty()) {
            loginState.value = LoginUiState.Fail("Password cannot be empty")
            return
        }
        loginState.value = LoginUiState.Loading

//        viewModelScope.launch(Dispatchers.IO) {
//            if (username.isNotEmpty() && password.isNotEmpty()) {
//                val result = authRepository.login(username, password)
//                when (result) {
//                    is DataLayerResponse.Success -> {
//                        loginState.value = LoginUiState.Success("Login successful")
//                    }
//
//                    is DataLayerResponse.Error -> {
//                        loginState.value = LoginUiState.Fail(result.message ?: "Check your username and password")
//                    }
//                }
//            }
//        }
    }

    fun onForgotPassword() {
        toast.value = ToastUiState.Success("No action available for Forgot password")
    }

    fun onLoginWithFacebook() {
        toast.value = ToastUiState.Success("No action available for Login with Facebook")
    }

    fun onSignup() {
        toast.value = ToastUiState.Success("No action available for Sign up")
    }
}

sealed class UsernameUiState {
    data class Success(val username: String) : UsernameUiState()
    data class Error(val message: String) : UsernameUiState()
    object Loading : UsernameUiState()
    object Empty : UsernameUiState()
}

sealed class PasswordUiState {
    data class Success(val username: String) : PasswordUiState()
    data class Error(val message: String) : PasswordUiState()
    object Loading : PasswordUiState()
    object Empty : PasswordUiState()
}

sealed class ToastUiState {
    data class Success(val message: String) : ToastUiState()
    object Empty : ToastUiState()
}

sealed class LoginUiState {
    data class Success(val message: String) : LoginUiState()
    data class Fail(val message: String) : LoginUiState()
    data object Loading : LoginUiState()
    data object Empty : LoginUiState()
}