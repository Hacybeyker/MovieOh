package com.hacybeyker.movieoh.ui.home

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import com.hacybeyker.movieoh.commons.base.BaseActivity
import com.hacybeyker.movieoh.databinding.ActivityHomeBinding
import com.hacybeyker.movieoh.ui.home.adapter.MovieSimilarAdapter
import com.hacybeyker.movieoh.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    private val adapterMovieSimilar: MovieSimilarAdapter by lazy { MovieSimilarAdapter() }

    override val viewBinding: ActivityHomeBinding
        get() = ActivityHomeBinding.inflate(layoutInflater)

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun setupView() {
        println("Here - ASV1")
        binding.rvMovieTopRated.adapter = adapterMovieSimilar
        println("Here - ASV2")
        binding.rvMovieTopRated.setHasFixedSize(true)
        println("Here - ASV3")
        binding.rvMovieTopRated.itemAnimator = DefaultItemAnimator()
        println("Here - ASV4")
    }

    override fun launchObservers() {
        println("Here - ALO1")
        viewModel.fetchTrendingMovie()
        println("Here - ALO2")
    }

    override fun setupObservers() {
        println("Here - ASO1")
        viewModel.trendingLiveData.observe(this) { trendingMovie ->
            println("Here - ASO2")
            binding.rvMovieTopRated.visibility = View.VISIBLE
            println("Here - ASO3")
            adapterMovieSimilar.submitList(trendingMovie.toMutableList())
            println("Here - ASO4")
            trendingMovie.forEach { movie ->
                println("Here - ${movie.title}")
            }
            println("Here - ASO7")
        }
    }
}
