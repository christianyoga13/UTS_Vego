package com.example.uts_vego

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {

    private lateinit var nameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var numberInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var signUpButton: Button
    private lateinit var alreadyHaveAccountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Views
        nameInputLayout = findViewById(R.id.nameInputLayout)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        numberInputLayout = findViewById(R.id.numberInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        signUpButton = findViewById(R.id.signUpButton)
        alreadyHaveAccountTextView = findViewById(R.id.alreadyHaveAccount)

        signUpButton.setOnClickListener {
            signUp()
        }

        alreadyHaveAccountTextView.setOnClickListener {
            // Handle navigation to MainActivity (Login screen)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signUp() {
        val name = nameInputLayout.editText?.text.toString().trim()
        val email = emailInputLayout.editText?.text.toString().trim()
        val number = numberInputLayout.editText?.text.toString().trim()
        val password = passwordInputLayout.editText?.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || number.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            // Add your sign-up logic here, such as storing the data or communicating with Firebase
            Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
        }
    }
}
