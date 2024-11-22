package com.example.uts_vego

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PromoScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Promo", color = Color.White, fontWeight = FontWeight.Bold) },
                backgroundColor = Color(0xFFFFA500)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Voucher Section
            Text("Voucher", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow {
                items(3) { // Replace '3' with the actual number of vouchers you have
                    PromoVoucherCard()
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Big Discount Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Big Discount", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                TextButton(onClick = { /* Handle See More click */ }) {
                    Text("See More", color = Color(0xFF4CAF50))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(listOf("Dharma Kitchen", "Fedwell")) { item -> // Replace with actual list data
                    BigDiscountItem(name = item)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun PromoVoucherCard() {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(100.dp),
        backgroundColor = Color(0xFF4CAF50),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text("Promo Cashback Tomoro...", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("• Tomoro Coffee - Cashback 45% Min. 50000", color = Color.White, fontSize = 10.sp)
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Rp5.000", color = Color.White, fontSize = 12.sp)
                Button(
                    onClick = { /* Handle Claim button click */ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Text("Claim", color = Color(0xFF4CAF50), fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun BigDiscountItem(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
            .padding(8.dp)
    ) {
        // Placeholder for the restaurant image
        Image(
            painter = painterResource(id = R.drawable.ic_restaurant), // Replace with actual image resource
            contentDescription = "Restaurant Image",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Chip(text = "Flexitarian")
                Chip(text = "Cheap")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("⭐ 4.7k", fontSize = 12.sp)
                Text("40 MINS", fontSize = 12.sp)
                Text("3.5 km", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun Chip(text: String) {
    Surface(
        color = Color(0xFFE0E0E0),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(2.dp)
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 10.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
