package com.rahullohra.instagram.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahullohra.instagram.auth.data.AuthRepository
import kotlinx.coroutines.flow.StateFlow

class UsernamePasswordViewmodelFactory (private val authRepository: AuthRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UsernamePasswordViewmodel(authRepository) as T
    }
}