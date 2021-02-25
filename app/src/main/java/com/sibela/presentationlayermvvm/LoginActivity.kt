package com.sibela.presentationlayermvvm

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeViewModel()
        setupObservers()
        loginButton.setOnClickListener { login() }
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this, LoginViewModel.LoginViewModelFactory())
            .get(LoginViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.loginEvent.observe(this, Observer { loginEvent ->
            when (loginEvent) {
                LoginViewModel.LoginEvent.InvalidUserOrPassword -> displayInvalidUserOrPassword()
                LoginViewModel.LoginEvent.LoggedIn -> displayLoggedIn()
                LoginViewModel.LoginEvent.Loading -> displayLoading()
                LoginViewModel.LoginEvent.UserNotFound -> displayUserNotFound()
            }
        })
    }

    private fun login() {
        val email = editTextTextEmailAddress.text.toString()
        val password = editTextTextPassword.text.toString()
        viewModel.login(email, password)
    }

    private fun displayLoading() {
        displayProgressBar()
        hideForm()
    }

    private fun hideForm() {
        textInputLayoutEmail.visibility = View.INVISIBLE
        textInputLayoutPassword.visibility = View.INVISIBLE
        editTextTextEmailAddress.visibility = View.INVISIBLE
        editTextTextPassword.visibility = View.INVISIBLE
    }

    private fun displayProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun displayUserNotFound() {
        hideProgressBar()
        displayForm()
        Toast.makeText(this, "User not found", Toast.LENGTH_LONG).show()
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun displayInvalidUserOrPassword() {
        hideProgressBar()
        displayForm()
        Toast.makeText(this, "Invalid user or password", Toast.LENGTH_LONG).show()
    }

    private fun displayForm() {
        textInputLayoutEmail.visibility = View.VISIBLE
        textInputLayoutPassword.visibility = View.VISIBLE
        editTextTextEmailAddress.visibility = View.VISIBLE
        editTextTextPassword.visibility = View.VISIBLE
    }

    private fun displayLoggedIn() {
        hideProgressBar()
        displayForm()
        Toast.makeText(this, "User logged in", Toast.LENGTH_LONG).show()
    }
}