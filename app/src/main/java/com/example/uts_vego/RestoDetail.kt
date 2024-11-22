package com.example.uts_vego

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RestoDetailScreen(navController: NavController, restoItem: RestoItem) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = restoItem.name, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color.White
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Gambar di latar belakang
            Image(
                painter = painterResource(id = restoItem.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Ketinggian gambar
                    .align(Alignment.TopCenter) // Letakkan di atas
            )

            // Konten utama
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 200.dp) // Beri ruang agar konten berada di bawah gambar
                    .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "${restoItem.name} - Gading Serpong",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color.Yellow,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = restoItem.rating.toString(),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "3.0 km",
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = R.drawable.motorcycle),
                                    contentDescription = "Delivery",
                                    tint = Color.Green,
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(text = "Delivery Order", fontSize = 12.sp)
                            }
                            Button(
                                onClick = { /* Handle Change */ },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
                            ) {
                                Text(text = "Change", fontSize = 12.sp, color = Color.Black)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Food discount up to 50%",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.body2
                                )
                                Text(
                                    text = "Buy 1 get 1",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.caption
                                )
                            }
                            Button(
                                onClick = { /* Handle Info */ },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                            ) {
                                Text(text = "Info", fontSize = 12.sp, color = Color.White)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "For You",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(getRecommendedItems()) { menu ->
                        MenuCard(menu = menu)
                    }
                }
            }
        }
    }
}


@Composable
fun MenuCard(menu: MenuItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        modifier = Modifier
            .width(150.dp)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = menu.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = menu.name,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Rp ${menu.price}",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
        }
    }
}

// Mock data
data class MenuItem(val name: String, val price: Int, val imageRes: Int)

fun getRecommendedItems(): List<MenuItem> {
    return listOf(
        MenuItem("Drumstick Vegetarian", 20000, R.drawable.vegan_food),
        MenuItem("Gyoza Vegetarian", 25000, R.drawable.vegan_food),
        MenuItem("Perkedel Vegetarian", 20000, R.drawable.vegan_food),
        MenuItem("Daging Vegetarian", 25000, R.drawable.vegan_food)
    )
}
