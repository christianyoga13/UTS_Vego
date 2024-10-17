package com.example.uts_vego

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    // Declaring variables for the views
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var googleSignInButton: Button
    private lateinit var registerNow: TextView
    private lateinit var forgotPassword: TextView
    private lateinit var rememberMeCheckBox: CheckBox
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize the views using findViewById
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signInButton = findViewById(R.id.signInButton)
        googleSignInButton = findViewById(R.id.googleSignInButton)
        registerNow = findViewById(R.id.registerNow)
        forgotPassword = findViewById(R.id.forgotPassword)
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox)

        // Sign In Button Action
        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Firebase sign-in logic
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, navigate to the MenuActivity or next screen
                            val intent = Intent(this, MenuActivity::class.java)
                            startActivity(intent)
                            finish() // Close this activity
                        } else {
                            // If sign-in fails, display a message to the user
                            Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        // Google Sign-In Button Action (Not implemented yet)
        googleSignInButton.setOnClickListener {
            Snackbar.make(it, "Google Sign-In not implemented yet", Snackbar.LENGTH_SHORT).show()
        }

        // Register Now Action
        registerNow.setOnClickListener {
            // Navigate to the SignUpActivity when "Register Now" is clicked
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Forgot Password Action
        forgotPassword.setOnClickListener {
            Toast.makeText(this, "Forgot password clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
