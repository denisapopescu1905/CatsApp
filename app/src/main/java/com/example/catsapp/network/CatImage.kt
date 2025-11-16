package com.example.catsapp.network
import com.squareup.moshi.Json

/**
 * Represents a cat image retrieved from TheCatAPI.
 *
 * @property id Unique identifier of the cat image.
 * @property url URL of the cat image.
 * @property breeds Optional list of [Breed] objects associated with this cat image.
 */
data class CatImage(
    val id: String? = null,
    val url: String? = null,
    val breeds: List<Breed>? = emptyList()
)

/**
 * Represents the breed information of a cat.
 *
 * @property id Unique identifier of the breed.
 * @property name Name of the breed.
 * @property origin Country or region where the breed originates.
 * @property temperament Description of the breed's temperament.
 * @property lifeSpan Average life span of the breed.
 * @property description Short description or overview of the breed.
 * @property wikipediaUrl URL to the Wikipedia page for the breed.
 * @property vetstreetUrl URL to the Vetstreet page for the breed.
 * @property intelligence Intelligence rating (1-5) of the breed.
 * @property affectionLevel Affection level rating (1-5) of the breed.
 * @property childFriendly Child friendliness rating (1-5) of the breed.
 * @property socialNeeds Social needs rating (1-5) of the breed.
 */
data class Breed(
    val id: String? = null,
    val name: String? = null,
    val origin: String? = null,
    val temperament: String? = null,
    @Json(name = "life_span") val lifeSpan: String? = null,
    val description: String? = null,
    @Json(name = "wikipedia_url") val wikipediaUrl: String? = null,
    @Json(name = "vetstreet_url") val vetstreetUrl: String? = null,
    val intelligence: Int? = null,
    @Json(name = "affection_level") val affectionLevel: Int? = null,
    @Json(name = "child_friendly") val childFriendly: Int? = null,
    @Json(name = "social_needs") val socialNeeds: Int? = null
)