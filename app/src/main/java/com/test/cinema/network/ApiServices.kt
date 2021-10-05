package com.test.cinema.network

import com.test.cinema.models.Cinema
import com.test.cinema.models.Movies
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    @GET("72f66f33-9186-4b20-a9a6-2c65661bc9cf")
    fun getMovies() : Call<Cinema>
}
