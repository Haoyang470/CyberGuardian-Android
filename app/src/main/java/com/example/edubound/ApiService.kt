package com.example.edubound

import retrofit2.http.GET

// Define a data mold
data class QuoteResponse(
    val q: String,
    val a: String
)

// Defining online contracts
interface ApiService {
    @GET("api/today")
    suspend fun getRandomQuote(): List<QuoteResponse>
}