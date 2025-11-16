package com.example.catsapp.network

/**
 * Repository class responsible for fetching cat images and related data from [CatOkHttpService].
 *
 * @property service The service used to make network requests to TheCatAPI.
 */
class CatRepository(
    private val service: CatOkHttpService
) {
    suspend fun getCats(): Result<List<CatImage>> {
        return service.fetchCats()
    }
}