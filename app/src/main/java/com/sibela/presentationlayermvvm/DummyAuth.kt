package com.sibela.presentationlayermvvm

import android.os.Handler

object DummyAuth {

    private const val VALID_EMAIL = "leo@company.com"
    private const val VALID_PASSWORD = "123456"

    fun login(email: String, password: String, callback: Callback) {
        Handler().postDelayed({
            if (email.trim().toLowerCase() == VALID_EMAIL) {
                if (password == VALID_PASSWORD) {
                    callback.loginSuccessful()
                } else {
                    callback.invalidUserOrPassword()
                }
            } else{
                callback.invalidUser()
            }
        }, 3000)
    }

    interface Callback {
        fun loginSuccessful()
        fun invalidUser()
        fun invalidUserOrPassword()
    }
}