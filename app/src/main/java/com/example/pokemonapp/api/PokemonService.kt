package com.example.pokemonapp.api

import com.example.pokemonapp.model.ResultPokemon
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon?")
    suspend fun getPokemonResult(@Query("offset") page: Int,
                                 @Query("limit") limit: Int
                                 ): ResultPokemon

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"

        //factory method
        fun create(): PokemonService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokemonService::class.java)
        }
    }
}