package com.example.catsapp
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Connectivity Observer, handles and updates states based on network connection
 */
class NetworkConnectivityObserver (
    context: Context
) : ConnectivityObserver {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback()
            {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(ConnectivityObserver.Status.Available)}
                }

                override fun onLosing(network: Network, maxMsToLivenetwork: Int) {
                    super.onLosing(network, maxMsToLivenetwork)
                    launch { send(ConnectivityObserver.Status.Losing)}
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityObserver.Status.Lost)}
                }
                override fun onUnavailable () {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.Status.Unavailable)}
                }
            }
            connectivityManager.registerDefaultNetworkCallback(callback)


            awaitClose{
                connectivityManager.unregisterNetworkCallback(callback)
            }

        }.distinctUntilChanged()
    }
}

interface ConnectivityObserver {
    fun observe(): Flow<Status>

    enum class Status
    {
        Available, Unavailable, Losing, Lost
    }
}

class ConnectivityViewModel(
    private val context: Context,
    private val networkObserver: NetworkConnectivityObserver,
) : ViewModel() {
    private val _connectivityStatus = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val connectivityStatus: StateFlow<ConnectivityObserver.Status> = _connectivityStatus
    private var lastStatus: ConnectivityObserver.Status? = null


    init {
        observeConnectivity()
    }

    fun observeConnectivity() {
        viewModelScope.launch {
            networkObserver.observe().collect { status ->
                if (lastStatus == null || lastStatus != status) {
                    showToast("Connectivity status changed: $status")
                }
                lastStatus = status
                _connectivityStatus.value = status
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}