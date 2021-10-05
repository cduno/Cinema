package com.test.cinema.repositories

import androidx.lifecycle.MutableLiveData
import com.test.cinema.interfaces.NetworkResponseCallback
import com.test.cinema.models.Cinema
import com.test.cinema.models.Movies
import com.test.cinema.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback

    private var mMoviesList: MutableLiveData<List<Movies>> =
        MutableLiveData<List<Movies>>().apply { value = emptyList() }

    companion object {
        private var mInstance: MoviesRepository? = null
        fun getInstance(): MoviesRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = MoviesRepository()
                }
            }
            return mInstance!!
        }
    }


    private lateinit var mMoviesCall: Call<Cinema>

    fun getMovies(callback: NetworkResponseCallback, forceFetch : Boolean): MutableLiveData<List<Movies>> {
        mCallback = callback
        if (mMoviesList.value!!.isNotEmpty() && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mMoviesList
        }


        mMoviesCall = RestClient.getInstance().getApiService().getMovies()
        mMoviesCall.enqueue(object : Callback<Cinema> {

            override fun onResponse(call: Call<Cinema>, response: Response<Cinema>) {
                var cinema=response.body()
                mMoviesList.value = cinema?.results
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<Cinema>, t: Throwable) {
                mMoviesList.value = emptyList()
                mCallback.onNetworkFailure(t)
            }

        })
        return mMoviesList
    }
}