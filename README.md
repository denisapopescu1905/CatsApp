# CatsApp 🐈

CatsApp is a Jetpack Compose Android application that displays a list of cat images along with breed information using [The Cat API](https://thecatapi.com/). Users can view detailed information about each cat, including temperament, life span, and ratings for intelligence, affection level, child-friendliness, and social needs.

---

## Features

- Fetches cat images from The Cat API.
- Displays breed information when available.
- Swipe-to-refresh to reload cat images.
- Detailed cat screen with:
  - Large image of the cat.
  - Breed name, origin, temperament, life span.
  - Ratings (up to 5 stars) for intelligence, affection level, child-friendliness, and social needs.
  - Description and links to Wikipedia and Vetstreet.
- Network connectivity detection:
  - Wi-Fi icon changes color (green = connected, red = disconnected).
  - Automatically reloads cats when internet connection is restored.
- Modern UI built entirely with Jetpack Compose.

---

## Screenshots
<img src="https://github.com/user-attachments/assets/2c72bcc1-493b-41e7-992c-07d1db2da6ac" width="300"/>
<img src="https://github.com/user-attachments/assets/0eaaceec-4e42-463a-b7d8-32e6f259f08c" width="300"/>
<img src="https://github.com/user-attachments/assets/47923c7d-eb07-4b35-bc15-e790da429765" width="300"/>
<img src="https://github.com/user-attachments/assets/3ed844bd-8bd4-4145-a912-e190440aaa07" width="300"/>
<img src="https://github.com/user-attachments/assets/52681225-c723-4882-9209-e4a68d25402a" width="300"/>
<img src="https://github.com/user-attachments/assets/c35c865a-5831-4485-a783-5e2cc80d86ef" width="300"/>

---

## Installation

1. Clone the repository:

```bash
git clone https://github.com/yourusername/CatsApp.git
cd CatsApp
```

3. Open the project in Android Studio.
   
4. Add your Cat API key in build.gradle.kts:
```bash
buildConfigField("String", "CAT_API_KEY", "\"YOUR_API_KEY_HERE\"")
```

6. Sync Gradle and build the project.
7. Run the app on an Android device or emulator.

---

## Usage

- On launch, the app fetches 20 cat images from [The Cat API](https://thecatapi.com/).
- Tap any cat image to view detailed breed information.
- Pull down the list to refresh and load new cats.
- Observe the Wi-Fi icon in the top app bar to monitor connectivity status.

---

## Dependencies

- Jetpack Compose
- Coil
- Accompanist SwipeRefresh
- OkHttp
- Moshi

---

## Project Structure

app/src/main/java/com/example/catsapp/
├── MainActivity.kt
├── navigation/
│ └── AppNavHost.kt
├── network/
│ ├── CatImage.kt
│ ├── CatRepository.kt
│ ├── CatsApiService.kt
│ ├── CatsViewModel.kt
│ └── NetworkConnectivityObserver.kt
└── screens/
├── CatDetailScreen.kt
└── CatListScreen.kt

