package com.example.uts_vego

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

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        carouselAdapter = CarouselAdapter(this, getCarouselItems())
        viewPager.adapter = carouselAdapter

        TabLayoutMediator(tabIndicator, viewPager) { tab, position ->
        }.attach()

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_home -> {
                    true
                }
                R.id.menu_payment -> {
                    Toast.makeText(this, "Payment clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_promo -> {
                    Toast.makeText(this, "Promo clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_profile -> {
                    Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
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
