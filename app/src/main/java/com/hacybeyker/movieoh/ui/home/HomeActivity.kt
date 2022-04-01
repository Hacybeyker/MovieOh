package com.hacybeyker.movieoh.ui.home

import android.view.View
import com.hacybeyker.movieoh.commons.base.BaseActivity
import com.hacybeyker.movieoh.databinding.ActivityHomeBinding
import com.hacybeyker.movieoh.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override val viewBinding: ActivityHomeBinding
        get() = ActivityHomeBinding.inflate(layoutInflater)

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun setupView() {
        viewModel.fetchTrendingMovie()
    }

    override fun setupObservers() {
        viewModel.trendingLiveData.observe(this) { trendingMovie ->
            trendingMovie.forEach { movie ->
                println("Here - ${movie.title}")
                binding.rvMovieTopRated.visibility = View.VISIBLE
            }
        }
    }
}
