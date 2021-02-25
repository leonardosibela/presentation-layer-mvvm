package com.sibela.presentationlayermvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModel : ViewModel() {

    private val _loginEvent = MutableLiveData<LoginEvent>()
    val loginEvent: LiveData<LoginEvent> = _loginEvent

    fun login(email: String, password: String) {
        _loginEvent.value = LoginEvent.Loading
        DummyAuth.login(email, password, object : DummyAuth.Callback {
            override fun loginSuccessful() {
                _loginEvent.value = LoginEvent.LoggedIn
            }

            override fun invalidUser() {
                _loginEvent.value = LoginEvent.UserNotFound
            }

            override fun invalidUserOrPassword() {
                _loginEvent.value = LoginEvent.InvalidUserOrPassword
            }
        })
    }

    sealed class LoginEvent {
        object Loading : LoginEvent()
        object UserNotFound : LoginEvent()
        object InvalidUserOrPassword : LoginEvent()
        object LoggedIn : LoginEvent()
    }

    class LoginViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor().newInstance()
        }
    }
}