package com.example.uts_vego

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            setContent {
                MainAppNavigation()
            }
        }
    }
}

@Composable
fun MainAppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf("home", "payment", "promo", "profile_home")) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreenContent(navController)
            }
            composable("payment") {
                PaymentScreen()
            }
            composable("promo") {
                PromoScreen(navController)
            }

            // Profile Related Routes
            composable("profile_home") {
                ProfileScreen(navController)
            }
            composable("yourProfile") {
                YourProfileScreen(navController)
            }
            composable("forum") {
                ForumContent(navController)
            }
            composable("onlineOrder") {
                OnlineOrderScreen(navController)
            }
            composable(
                route = "restoDetail/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType })
            ) { backStackEntry ->
                val restoName = backStackEntry.arguments?.getString("name") ?: ""

                // Gabungkan semua data dari berbagai fungsi
                val allItems = getRestoItems() + getFastServeItems() + getBigDiscountItems()

                val restoItem = allItems.find { it.name == restoName }

                if (restoItem != null) {
                    RestoDetailScreen(navController = navController, restoItem = restoItem)
                } else {
                    Text("Resto not found") // Handle jika tidak ada data
                }
            }

        }
    }
}

