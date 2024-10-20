package com.example.uts_vego

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var nameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var numberInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var signUpButton: Button
    private lateinit var alreadyHaveAccountTextView: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

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
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeScreen::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Sign Up failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
