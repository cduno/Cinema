package com.test.cinema.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.cinema.R
import com.test.cinema.adapters.MoviesListAdapter
import com.test.cinema.databinding.ActivityMoviesListBinding
import com.test.cinema.viewmodels.MoviesListViewModel

class MoviesListActivity : AppCompatActivity() {

    private lateinit var mAdapter: MoviesListAdapter
    private lateinit var mViewModel: MoviesListViewModel
    private lateinit var mActivityBinding: ActivityMoviesListBinding
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movies_list)

        mViewModel = ViewModelProviders.of(this).get(MoviesListViewModel::class.java)

        mActivityBinding.viewModel = mViewModel
        mActivityBinding.lifecycleOwner = this
        supportActionBar?.title = "All Movies and Series"
        initializeRecyclerView()
        initializeObservers()
        initializeNavBotton()
    }

    private fun initializeNavBotton(){
        bottomNavigation = findViewById(R.id.navigationView)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_all -> {
                    mAdapter.showListByCatagory("all")
                    supportActionBar?.title = "All Movies and Series"
                }
                R.id.navigation_movie -> {
                    mAdapter.showListByCatagory("MOVIE")
                    supportActionBar?.title = "Movies"
                }
                R.id.navigation_series -> {
                    mAdapter.showListByCatagory("SERIES")
                    supportActionBar?.title = "Series"
                }
            }
            true
        }
    }

    private fun initializeRecyclerView() {
        mAdapter = MoviesListAdapter()
        mActivityBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun initializeObservers() {
        mViewModel.fetchCountriesFromServer(false).observe(this, Observer { kt ->
            mAdapter.setData(kt)
        })
        mViewModel.mShowApiError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(it).show()
        })
        mViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                mActivityBinding.progressBar.visibility = View.VISIBLE
                mActivityBinding.floatingActionButton.hide()
            } else {
                mActivityBinding.progressBar.visibility = View.GONE
                mActivityBinding.floatingActionButton.show()
            }
        })
        mViewModel.mShowNetworkError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
        })
    }

        fun getAdapter() : MoviesListAdapter{
            return mAdapter
        }
}