package fr.alemanflorian.eventplanner.user

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppUserViewModel :ViewModel() {

    val user: MutableState<AppUser?> = mutableStateOf(null)
    val loading: MutableState<Boolean> = mutableStateOf(true)

    init {
        viewModelScope.launch {
            user.value = AppUser.load()
            loading.value = false
            println("${System.currentTimeMillis()} : AppUserViewModel.userLoaded ${user.value}")
        }
    }

    fun setValue(user: AppUser?)
    {
        this.user.value = user
    }
}