package com.example.uts_vego

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class Profile : AppCompatActivity() {

    // Firebase instances
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    // UI elements
    private lateinit var toolbar: Toolbar
    private lateinit var profileImageView: ImageView
    private lateinit var buttonEditImage: Button
    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextDOB: TextInputEditText
    private lateinit var editTextPOB: TextInputEditText
    private lateinit var buttonSave: Button
    private lateinit var bottomNavigationView: BottomNavigationView

    // Calendar instance for DOB
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Initialize UI elements
        toolbar = findViewById(R.id.toolbar_profile)
        profileImageView = findViewById(R.id.profileImageView)
        buttonEditImage = findViewById(R.id.buttonEditImage)
        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextDOB = findViewById(R.id.editTextDOB)
        editTextPOB = findViewById(R.id.editTextPOB)
        buttonSave = findViewById(R.id.buttonSave)
        bottomNavigationView = findViewById(R.id.bottomNavigationView_profile)

        // Set up the toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Show back button
        supportActionBar?.title = "Profile"

        // Load user data
        loadUserProfile()

        // Handle DOB field click to show DatePicker
        editTextDOB.setOnClickListener {
            showDatePickerDialog()
        }

        // Handle Save button click
        buttonSave.setOnClickListener {
            saveUserProfile()
        }

        // Optional: Handle Edit Image button click
        buttonEditImage.setOnClickListener {
            // Implement image selection and upload functionality
            Toast.makeText(this, "Edit Image Clicked", Toast.LENGTH_SHORT).show()
        }

        // Set up BottomNavigationView
        setupBottomNavigationView()
    }

    // Handle the back button in the toolbar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // Function to load user profile from Firestore
    private fun loadUserProfile() {
        val user = auth.currentUser
        if (user != null) {
            val uid = user.uid
            firestore.collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Assuming Firestore fields: name, email, dateOfBirth, placeOfBirth
                        editTextName.setText(document.getString("name") ?: "")
                        editTextEmail.setText(document.getString("email") ?: user.email)
                        editTextDOB.setText(document.getString("dateOfBirth") ?: "")
                        editTextPOB.setText(document.getString("placeOfBirth") ?: "")
                        // Load profile image if stored (Optional)
                        // val profileImageUrl = document.getString("profileImageUrl")
                        // if (profileImageUrl != null) {
                        //     // Use an image loading library like Glide or Picasso to load the image
                        //     Glide.with(this).load(profileImageUrl).into(profileImageView)
                        // }
                    } else {
                        Toast.makeText(this, "No profile found.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error loading profile: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show()
            // Redirect to LoginActivity if needed
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    // Function to save user profile to Firestore
    private fun saveUserProfile() {
        val user = auth.currentUser
        if (user != null) {
            val uid = user.uid

            // Retrieve input data
            val name = editTextName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val dateOfBirth = editTextDOB.text.toString().trim()
            val placeOfBirth = editTextPOB.text.toString().trim()

            // Validate inputs
            if (name.isEmpty()) {
                editTextName.error = "Name is required"
                editTextName.requestFocus()
                return
            }

            if (email.isEmpty()) {
                editTextEmail.error = "Email is required"
                editTextEmail.requestFocus()
                return
            }

            // Update Firebase Authentication email if changed
            if (email != user.email) {
                user.updateEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Email updated successfully.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failed to update email: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

            // Create a user profile map
            val userProfile = hashMapOf(
                "name" to name,
                "email" to email,
                "dateOfBirth" to dateOfBirth,
                "placeOfBirth" to placeOfBirth
                // Add more fields if needed
            )

            // Save to Firestore
            firestore.collection("users").document(uid)
                .set(userProfile)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile updated successfully.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error updating profile: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show()
            // Redirect to LoginActivity if needed
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    // Function to show DatePicker dialog
    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = android.app.DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Update Calendar instance
            calendar.set(Calendar.YEAR, selectedYear)
            calendar.set(Calendar.MONTH, selectedMonth)
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay)

            // Format and set the date to EditText
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            editTextDOB.setText(sdf.format(calendar.time))
        }, year, month, day)

        datePicker.show()
    }

    // Function to set up BottomNavigationView
    private fun setupBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_home -> {
                    // Jika sudah di Profile, buka HomeScreen
                    val intent = Intent(this, HomeScreen::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    true
                }
                R.id.menu_payment -> {
                    // Navigasi ke Payment Activity (implementasikan jika ada)
                    Toast.makeText(this, "Payment clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_promo -> {
                    // Navigasi ke Promo Activity (implementasikan jika ada)
                    Toast.makeText(this, "Promo clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_profile -> {
                    // Sudah di Profile, lakukan sesuatu jika diperlukan
                    true
                }
                else -> false
            }
        }

        // Set selected item
        bottomNavigationView.selectedItemId = R.id.menu_profile
    }
}
