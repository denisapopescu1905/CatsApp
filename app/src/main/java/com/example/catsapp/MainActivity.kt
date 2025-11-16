package com.example.catsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.catsapp.navigation.AppNavHost
import com.example.catsapp.network.CatOkHttpService
import com.example.catsapp.network.CatRepository
import com.example.catsapp.network.CatsViewModel
import com.example.catsapp.ui.theme.CatsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val service = CatOkHttpService(apiKey = BuildConfig.CAT_API_KEY)
        val repository = CatRepository(service)
        val catsViewModel = CatsViewModel(repository)
        val networkObserver = NetworkConnectivityObserver(context = this)
        val connectivityViewModel = ConnectivityViewModel(context = this, networkObserver)

        setContent {
            CatsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val cats by catsViewModel.cats.collectAsState()
                    val connectivityStatus by connectivityViewModel.connectivityStatus.collectAsState()
                    LaunchedEffect(connectivityStatus) {
                        if (connectivityStatus == ConnectivityObserver.Status.Available) {
                            catsViewModel.loadCats()
                        }
                    }

                    AppNavHost(
                        cats = cats,
                        connectivityViewModel = connectivityViewModel,
                        onRefresh = { catsViewModel.loadCats() }
                    )
                }
            }
        }
    }
}