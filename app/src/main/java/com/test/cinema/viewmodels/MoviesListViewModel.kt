package com.test.cinema.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.cinema.interfaces.NetworkResponseCallback
import com.test.cinema.models.Cinema
import  com.test.cinema.models.Movies
import  com.test.cinema.repositories.MoviesRepository
import  com.test.cinema.utils.NetworkHelper

class MoviesListViewModel(private val app: Application) : AndroidViewModel(app) {
    private var mList: MutableLiveData<List<Movies>> =
        MutableLiveData<List<Movies>>().apply { value = emptyList() }
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()

    private var mRepository = MoviesRepository.getInstance()

    fun fetchCountriesFromServer(forceFetch: Boolean): MutableLiveData<List<Movies>> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            mList = mRepository.getMovies(object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                }
            }, forceFetch)
        } else {
            mShowNetworkError.value = true
        }
        return mList
    }

    fun onRefreshClicked(view: View) {
        fetchCountriesFromServer(true)
    }
}