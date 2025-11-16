import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.catsapp.network.CatImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

/**
 * Displays the detailed view of a single cat, including its image, breed information,
 * ratings, description, and links to Wikipedia and Vetstreet (if available).
 * The content is scrollable to accommodate long descriptions or multiple UI elements.
 * @param cat The CatImage object containing the image URL and breed information.
 */
@Composable
fun CatDetailScreen(cat: CatImage) {
    val breed = cat.breeds?.firstOrNull()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Big Image
        Image(
            painter = rememberAsyncImagePainter(cat.url),
            contentDescription = breed?.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(16.dp))

        // Name + Origin
        Text(text = breed?.name ?: "Unknown", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Origin: ${breed?.origin ?: "Unknown"}")

        Spacer(Modifier.height(12.dp))

        // Temperament
        Text(
            text = "Temperament: ${breed?.temperament ?: "Unknown"}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(12.dp))

        // Life span
        Text(text = "Life span: ${breed?.lifeSpan ?: "-"} years")

        Spacer(Modifier.height(16.dp))

        // ⭐ Ratings
        RatingRow("Intelligence", breed?.intelligence)
        RatingRow("Affection Level", breed?.affectionLevel)
        RatingRow("Child Friendly", breed?.childFriendly)
        RatingRow("Social Needs", breed?.socialNeeds)

        Spacer(Modifier.height(16.dp))

        // Description
        Text(
            text = breed?.description ?: "",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(20.dp))

        // Interactive labels (Wikipedia + Vetstreet)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            breed?.wikipediaUrl?.let { url ->
                AssistChip(
                    onClick = {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    },
                    label = { Text("Wikipedia") }
                )
            }

            breed?.vetstreetUrl?.let { url ->
                AssistChip(
                    onClick = {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    },
                    label = { Text("Vetstreet") }
                )
            }
        }
    }
}

/**
 * Displays a single row of rating stars for a specific cat attribute.
 *
 * @param title The name of the rating attribute (e.g., "Intelligence").
 * @param value The rating value (0-5). Null is treated as 0.
 */
@Composable
fun RatingRow(title: String, value: Int?) {
    Column(Modifier.padding(vertical = 4.dp)) {
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
        Row {
            val filled = value ?: 0
            for (i in 1..5) {
                if (i <= filled) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107)
                    )
                } else {
                    Icon(
                        Icons.Filled.StarBorder,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}
