package com.example.kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.kotlin.ApiClient
import android.util.Log


class RegistrationActivity : AppCompatActivity() {

    private val TAG = "RegistrationActivity"


    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText

    private lateinit var buttonRegister: Button
    private lateinit var textViewError: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)


        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)

        textViewError = findViewById(R.id.textViewError)
        progressBar = findViewById(R.id.progressBar)
        buttonRegister = findViewById(R.id.buttonRegister)




        buttonRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {

        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()


        Log.d("RegistrationData", "Email: $email, Password: $password")




        showLoading()

        val apiService = ApiClient.getApiService()
        val request = RegistrationRequest(email, password)

        apiService.registerUser(request).enqueue(object : Callback<RegistrationResponse> {
            override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                hideLoading()
                if (response.isSuccessful) {
                    Log.d(TAG, "Registration successful for user: $email")

                    val intent = Intent(this@RegistrationActivity, UserListActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {

                    val errorBody = response.errorBody()?.string()

                    Log.e(TAG, "Registration failed: ${response.code()} - ${response.message()} - $errorBody")

                    showError("Registration failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                hideLoading()
                Log.e(TAG, "Network error: ${t.message}")

                showError("Network error: ${t.message}")
            }
        })
    }

    private fun showError(message: String) {
        textViewError.text = message
        textViewError.visibility = View.VISIBLE
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        buttonRegister.isEnabled = false
        textViewError.visibility = View.GONE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
        buttonRegister.isEnabled = true
    }
}
