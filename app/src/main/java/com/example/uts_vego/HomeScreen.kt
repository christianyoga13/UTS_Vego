package com.example.uts_vego

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeScreen : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabIndicator: TabLayout
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var carouselAdapter: CarouselAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewPager = findViewById(R.id.carouselViewPager)
        tabIndicator = findViewById(R.id.tabIndicator)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set up the carousel
        carouselAdapter = CarouselAdapter(this, getCarouselItems())
        viewPager.adapter = carouselAdapter

        TabLayoutMediator(tabIndicator, viewPager) { tab, position ->
            // Optionally set tab titles
        }.attach()

        // Handle BottomNavigationView item selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_home -> {
                    // If already on Home, you might want to refresh or do nothing
                    true
                }
                R.id.menu_payment -> {
                    // Navigate to Payment Activity or Fragment
                    Toast.makeText(this, "Payment clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_promo -> {
                    // Navigate to Promo Activity or Fragment
                    Toast.makeText(this, "Promo clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_profile -> {
                    // Navigate to Profile Activity
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        setupButtons()
    }

    private fun getCarouselItems(): List<Int> {
        return listOf(
            R.drawable.logo,
            R.drawable.bicycle,
            R.drawable.btn_1
        )
    }

    private fun setupButtons() {
        findViewById<LinearLayout>(R.id.button1).setOnClickListener {
            Toast.makeText(this, "Button 1 clicked", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.button2).setOnClickListener {
            Toast.makeText(this, "Button 2 clicked", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.button3).setOnClickListener {
            Toast.makeText(this, "Button 3 clicked", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.button4).setOnClickListener {
            Toast.makeText(this, "Button 4 clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
