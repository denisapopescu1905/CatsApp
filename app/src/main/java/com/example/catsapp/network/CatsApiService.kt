package com.example.catsapp.network
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.MalformedURLException

private const val TAG = "CatOkHttpService"
private const val BASE_URL = "https://api.thecatapi.com/v1/images/search?limit=20&has_breeds=1"

/**
 * Sealed class representing success or failure of a network call.
 */
sealed class Result<out T> {

    data class Ok<out T>(val value: T) : Result<T>()

    data class Err(val message: String) : Result<Nothing>()

    val isOk get() = this is Ok
    val isErr get() = this is Err

    fun getOrNull(): T? = when (this) {
        is Ok -> value
        is Err -> null
    }
}

/**
 * Service responsible for fetching cat images from TheCatAPI using OkHttp.
 *
 * @param apiKey Optional API key for TheCatAPI.
 */
class CatOkHttpService(private val apiKey: String? = null) {


    private val client: OkHttpClient = OkHttpClient.Builder()
        .followRedirects(false)
        .followSslRedirects(false)
        .build()


    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val adapter = moshi.adapter<List<CatImage>>(Types.newParameterizedType(List::class.java, CatImage::class.java))


    /**
     * Fetches 20 cat images, including breed info if available.
     * Automatically handles HTTP 302 redirects up to [maxRedirects].
     * @param maxRedirects Maximum number of HTTP redirects to follow.
     * @return [Result.Ok] with a list of [CatImage] on success, or [Result.Err] on failure.
     */
    suspend fun fetchCats(maxRedirects: Int = 5): Result<List<CatImage>> = withContext(Dispatchers.IO) {
        var url = BASE_URL
        var redirectCount = 0
        try {
            var response: okhttp3.Response
            do {
                val reqBuilder = Request.Builder().url(url)
                if (!apiKey.isNullOrBlank()) reqBuilder.addHeader("x-api-key", apiKey)
                val request = reqBuilder.get().build()

                Log.d(TAG, "Requesting: $url")
                response = client.newCall(request).execute()

                if (response.isSuccessful || response.code != 302)
                    break

                val newLocation = response.header("Location")
                if (newLocation != null) {
                    url = newLocation
                    redirectCount++
                } else {
                    return@withContext Result.Err("Redirect without Location header")
                }
            } while (redirectCount < maxRedirects)


            val body = response.body?.string()
            Log.d(TAG, "Response: " + body.toString())
            if (response.isSuccessful && !body.isNullOrBlank()) {
                val list = adapter.fromJson(body) ?: emptyList()
                return@withContext Result.Ok(list)
            }

            val code = response.code
            val msg = response.message
            return@withContext Result.Err("HTTP $code: $msg")

        } catch (e: MalformedURLException) {
            Log.e(TAG, "Bad URL", e)
            return@withContext Result.Err("Bad URL: ${e.localizedMessage}")
        } catch (e: Exception) {
            Log.e(TAG, "Network error", e)
            return@withContext Result.Err("Network error: ${e.localizedMessage}")
        }
    }
}