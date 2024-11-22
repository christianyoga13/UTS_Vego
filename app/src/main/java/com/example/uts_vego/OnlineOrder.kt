package com.example.uts_vego

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

data class CarouselItem(
    val imageResId: Int,
    val title: String
)


class OnlineOrder : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainAppNavigation()
        }
    }
}

@Composable
fun OnlineOrderScreen(navController: NavController) {
    val items = listOf(
        CarouselItem(R.drawable.heavy_meal, "Heavy Meal"),
        CarouselItem(R.drawable.snack, "Snack"),
        CarouselItem(R.drawable.ic_cart, "Groceries"),
        CarouselItem(R.drawable.drink, "Drink")
    )

    Scaffold(
        topBar = {
            TopBarWithSearchBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "Kategori", modifier = Modifier.padding(16.dp), fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Carousel(items = items)
            Spacer(modifier = Modifier.height(24.dp))
            ButtonGroup()
            Spacer(modifier = Modifier.height(24.dp))
            OrderNowCarousel()
            Spacer(modifier = Modifier.height(24.dp))
            ReusableRestoSection(
                title = "24 Hours",
                items = getRestoItems(),
                onSeeAllClick = { /* Handle See All */ }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ReusableRestoSection(
                title = "Fast Serve",
                items = getFastServeItems(),
                onSeeAllClick = { /* Handle See All */ }
            )
        }
    }
}

@Composable
fun TopBarWithSearchBar(navController: NavController) {
    Column(modifier = Modifier.background(Color(0xFFFFA500))) {
        TopAppBar(
            title = {
                Text(text = "Online Order", color = Color.White)
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            },
            backgroundColor = Color(0xFFFFA500),
            elevation = 0.dp
        )

        SearchBar(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "Search Icon")
        },
        placeholder = { Text("Let's order something!") },
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp)),
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        )
    )
}

@Composable
fun Carousel(items: List<CarouselItem>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            CarouselCard(item)
        }
    }
}

@Composable
fun CarouselCard(item: CarouselItem) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(16.dp))
            .clickable { /* Handle click */ }
    ) {
        Image(
            painter = painterResource(id = item.imageResId),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun ButtonGroup() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonOneByOne(
                title = "Open 24 Hours",
                description = "Ready anytime",
                icon = Icons.Default.AccessTime,
                iconColor = Color.Green
            )
            ButtonOneByOne(
                title = "Fast Serve",
                description = "Serve for u",
                icon = Icons.Default.Restaurant,
                iconColor = Color.Red

            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonOneByOne(
                title = "Big Discount",
                description = "Discount up to 50%",
                icon = Icons.Default.Percent,
                iconColor =  Color.Blue
            )
            ButtonOneByOne(
                title = "Best Seller",
                description = "Recommended",
                icon = Icons.Default.Star,
                iconColor = Color.Yellow
            )
        }
    }
}

@Composable
fun ButtonOneByOne(
    title: String,
    description: String,
    icon: ImageVector,
    iconColor: Color
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        modifier = Modifier
            .width(160.dp)
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Bottom)
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OrderNowCarousel() {
    val pagerState = rememberPagerState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFECECEC))
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            // Header Order Now
            Text(
                text = "Order Now",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalPager(
                count = getOrderItems().size, // Jumlah item
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 8.dp), // Padding untuk snap
            ) { page ->
                OrderCard(orderItem = getOrderItems()[page])
            }
        }
    }
}

@Composable
fun OrderCard(orderItem: OrderItem) {
    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(horizontal = 32.dp)
    ) {
        Column {
            // Gambar di atas
            Image(
                painter = painterResource(id = orderItem.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            // Teks di bawah
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = orderItem.title,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = orderItem.subtitle,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )
            }
        }
    }
}

fun getOrderItems(): List<OrderItem> {
    return listOf(
        OrderItem(R.drawable.carousel_image1, "Salad Buah Murah, Cuma 10k", "Ad - Salad Buah Eni"),
        OrderItem(R.drawable.carousel_image2, "Fresh Juice, Mulai 15k", "Ad - Juice Segar"),
        OrderItem(R.drawable.carousel_image1, "Roti Panggang Enak", "Ad - Toast House")
    )
}

data class OrderItem(
    val imageRes: Int,
    val title: String,
    val subtitle: String
)

@Composable
fun ReusableRestoSection(
    title: String, // Judul section
    items: List<RestoItem>, // Data untuk kartu
    onSeeAllClick: () -> Unit // Aksi ketika tombol "See All" diklik
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Header "24 Hours" dan tombol "See All"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = onSeeAllClick,
                modifier = Modifier.background(
                    color = Color(0xFFECECEC),
                    shape = RoundedCornerShape(12.dp)
                )
            ) {
                Text(
                    text = "See All",
                    color = Color.Green,
                    style = MaterialTheme.typography.body2
                )
            }
        }

        // Carousel Horizontal
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { item ->
                RestoCard(restoItem = item)
            }
        }
    }
}

@Composable
fun RestoCard(restoItem: RestoItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .width(180.dp)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = restoItem.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = restoItem.name,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Rating dan jarak
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = restoItem.rating.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${restoItem.time} • ${restoItem.distance}",
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Kategori Label
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                restoItem.tags.forEach { tag ->
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                color = Color.Green,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MultiSectionScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopBarWithSearchBar(navController)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ReusableRestoSection(
                    title = "24 Hours",
                    items = getRestoItems(),
                    onSeeAllClick = { /* Handle See All for 24 Hours */ }
                )
            }
            item {
                ReusableRestoSection(
                    title = "Fast Serve",
                    items = getFastServeItems(),
                    onSeeAllClick = { /* Handle See All for Fast Serve */ }
                )
            }
            item {
                ReusableRestoSection(
                    title = "Big Discount",
                    items = getBigDiscountItems(),
                    onSeeAllClick = { /* Handle See All for Big Discount */ }
                )
            }
            item {
                ReusableRestoSection(
                    title = "Best Seller",
                    items = getBestSellerItems(),
                    onSeeAllClick = { /* Handle See All for Best Seller */ }
                )
            }
        }
    }
}

data class RestoItem(
    val imageRes: Int,
    val name: String,
    val rating: Double,
    val time: String,
    val distance: String,
    val tags: List<String>
)

fun getFastServeItems(): List<RestoItem> {
    return listOf(
        RestoItem(
            imageRes = R.drawable.vegan_food,
            name = "Vegetarian Mix",
            rating = 4.8,
            time = "15 MINS",
            distance = "0.8 Km",
            tags = listOf("Healthy", "Cheap")
        ),
        RestoItem(
            imageRes = R.drawable.resto_image,
            name = "Asian Delight",
            rating = 4.7,
            time = "10 MINS",
            distance = "1.0 Km",
            tags = listOf("Quick Serve", "Popular")
        )
    )
}

fun getBigDiscountItems(): List<RestoItem> {
    return listOf(
        RestoItem(
            imageRes = R.drawable.vegan_smoothie,
            name = "Losing Hut",
            rating = 4.5,
            time = "30 MINS",
            distance = "2.0 Km",
            tags = listOf("Cheap", "Discount")
        ),
        RestoItem(
            imageRes = R.drawable.vegan_food,
            name = "Festival",
            rating = 4.3,
            time = "25 MINS",
            distance = "1.5 Km",
            tags = listOf("Deal", "Limited")
        )
    )
}

fun getBestSellerItems(): List<RestoItem> {
    return listOf(
        RestoItem(
            imageRes = R.drawable.vegan_food,
            name = "Resto Salad Sayur",
            rating = 4.9,
            time = "20 MINS",
            distance = "1.2 Km",
            tags = listOf("Best Resto", "Top Rated")
        ),
        RestoItem(
            imageRes = R.drawable.resto_image,
            name = "RM. Vegan Indo",
            rating = 4.8,
            time = "15 MINS",
            distance = "1.0 Km",
            tags = listOf("Recommended", "Vegan")
        )
    )
}

fun getRestoItems(): List<RestoItem> {
    return listOf(
        RestoItem(
            imageRes = R.drawable.resto_image,
            name = "Resto Salad Sayur",
            rating = 4.5,
            time = "20 MINS",
            distance = "1.5 Km",
            tags = listOf("Best Resto", "Cheap")
        ),
        RestoItem(
            imageRes = R.drawable.vegan_food,
            name = "RM. Vegan Indo",
            rating = 4.7,
            time = "20 MINS",
            distance = "1.5 Km",
            tags = listOf("Near You", "Recommend")
        ),
        RestoItem(
            imageRes = R.drawable.resto_image,
            name = "Resto Jepang Fusion",
            rating = 4.8,
            time = "25 MINS",
            distance = "2.0 Km",
            tags = listOf("Flexitarian", "Popular")
        )
    )
}

@Preview(showBackground = true)
@Composable
fun OnlineOrderPreview() {
    MaterialTheme {
        OnlineOrderScreen(navController = rememberNavController())
    }
}
