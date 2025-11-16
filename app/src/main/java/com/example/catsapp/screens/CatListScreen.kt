package com.example.catsapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.catsapp.ConnectivityObserver
import com.example.catsapp.ConnectivityViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.catsapp.network.CatImage


/**
 * Displays a scrollable list of cat images with a header and pull-to-refresh functionality.
 *
 * The header includes the app name and a Wi-Fi status icon that changes color based on
 * the connectivity state provided by [ConnectivityViewModel].
 *
 * @param cats List of [CatImage] objects to display.
 * @param onCatClick Callback invoked when a cat image is clicked.
 * @param isRefreshing Boolean indicating whether a refresh is in progress.
 * @param connectivityViewModel ViewModel providing connectivity status.
 * @param onRefresh Callback invoked when the user performs a pull-to-refresh gesture.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatListScreen(
    cats: List<CatImage>,
    onCatClick: (CatImage) -> Unit,
    isRefreshing: Boolean,
    connectivityViewModel: ConnectivityViewModel,
    onRefresh: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // Header
        TopAppBar(
            title = {
                Text(
                    text = "CatsApp",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            actions = {
                val status by connectivityViewModel.connectivityStatus.collectAsState()
                val color = when (status) {
                    ConnectivityObserver.Status.Available -> Color.Green
                    ConnectivityObserver.Status.Losing -> Color.Yellow
                    ConnectivityObserver.Status.Lost,
                    ConnectivityObserver.Status.Unavailable -> Color.Red
                }
                Icon(
                    imageVector = Icons.Filled.Wifi,
                    contentDescription = "Wi-Fi Status",
                    tint = color,
                    modifier = Modifier.padding(end = 16.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6200EE)) // Purple header
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cats) { cat ->
                    CatItem(cat = cat, onClick = { onCatClick(cat) })
                }
            }
        }
    }
}

/**
 * Displays a single cat image item in the list.
 *
 * Clicking on the image triggers the provided [onClick] callback.
 *
 * @param cat The [CatImage] to display.
 * @param onClick Callback invoked when the image is clicked.
 */
@Composable
fun CatItem(cat: CatImage, onClick: () -> Unit) {
    Image(
        painter = rememberAsyncImagePainter(cat.url),
        contentDescription = cat.breeds?.firstOrNull()?.name ?: "Cat",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )
}
