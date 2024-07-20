package com.hacybeyker.movieoh.ui.home

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import com.hacybeyker.movieoh.R
import com.hacybeyker.movieoh.commons.base.BaseActivity
import com.hacybeyker.movieoh.databinding.ActivityHomeBinding
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.ui.OnItemMovie
import com.hacybeyker.movieoh.ui.home.adapter.MovieAdapter
import com.hacybeyker.movieoh.ui.home.viewmodel.HomeViewModel
import com.hacybeyker.movieoh.ui.movie.MovieActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), OnItemMovie {
    private val adapterUpcoming: MovieAdapter by lazy { MovieAdapter { onClickMovie(it) } }
    private val adapterTrending: MovieAdapter by lazy { MovieAdapter { onClickMovie(it) } }
    private val adapterAction: MovieAdapter by lazy { MovieAdapter { onClickMovie(it) } }
    private val adapterAnimation: MovieAdapter by lazy { MovieAdapter { onClickMovie(it) } }
    private val adapterComedy: MovieAdapter by lazy { MovieAdapter { onClickMovie(it) } }
    private val adapterDrama: MovieAdapter by lazy { MovieAdapter { onClickMovie(it) } }
    private val adapterAdventure: MovieAdapter by lazy { MovieAdapter { onClickMovie(it) } }

    override val viewBinding: ActivityHomeBinding
        get() = ActivityHomeBinding.inflate(layoutInflater)

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun setupStatusBar() {
        window.statusBarColor = getColor(R.color.black)
    }

    override fun setupView() {
        binding.apply {
            rvMovieUpcoming.adapter = adapterUpcoming
            rvMovieUpcoming.setHasFixedSize(true)
            rvMovieUpcoming.itemAnimator = DefaultItemAnimator()

            rvMovieTrending.adapter = adapterTrending
            rvMovieTrending.setHasFixedSize(true)
            rvMovieTrending.itemAnimator = DefaultItemAnimator()

            rvMovieAction.adapter = adapterAction
            rvMovieAction.setHasFixedSize(true)
            rvMovieAction.itemAnimator = DefaultItemAnimator()

            rvMovieAnimation.adapter = adapterAnimation
            rvMovieAnimation.setHasFixedSize(true)
            rvMovieAnimation.itemAnimator = DefaultItemAnimator()

            rvMovieComedy.adapter = adapterComedy
            rvMovieComedy.setHasFixedSize(true)
            rvMovieComedy.itemAnimator = DefaultItemAnimator()

            rvMovieDrama.adapter = adapterDrama
            rvMovieDrama.setHasFixedSize(true)
            rvMovieDrama.itemAnimator = DefaultItemAnimator()

            rvMovieAdventure.adapter = adapterAdventure
            rvMovieAdventure.setHasFixedSize(true)
            rvMovieAdventure.itemAnimator = DefaultItemAnimator()
        }
    }

    override fun launchObservers() {
        viewModel.initHome()
    }

    override fun setupObservers() {
        viewModel.apply {
            loadingLiveData.observe(this@HomeActivity) { loading ->
                when (loading) {
                    true -> {
                        binding.sflContainerHome.visibility = View.VISIBLE
                        binding.nsvMainHomeScroll.visibility = View.GONE
                        binding.sflContainerHome.startShimmer()
                    }

                    else -> {
                        binding.sflContainerHome.visibility = View.GONE
                        binding.nsvMainHomeScroll.visibility = View.VISIBLE
                        binding.sflContainerHome.stopShimmer()
                    }
                }
            }

            trendingLiveData.observe(this@HomeActivity) { trendingMovie ->
                binding.rvMovieTrending.visibility = View.VISIBLE
                adapterTrending.submitList(trendingMovie.toMutableList())
            }

            upcomingLiveData.observe(this@HomeActivity) { upcoming ->
                binding.rvMovieUpcoming.visibility = View.VISIBLE
                adapterUpcoming.submitList(upcoming.toMutableList())
            }

            actionLiveData.observe(this@HomeActivity) { action ->
                binding.rvMovieAction.visibility = View.VISIBLE
                adapterAction.submitList(action.toMutableList())
            }

            animationLiveData.observe(this@HomeActivity) { animation ->
                binding.rvMovieAnimation.visibility = View.VISIBLE
                adapterAnimation.submitList(animation.toMutableList())
            }

            comedyLiveData.observe(this@HomeActivity) { comedy ->
                binding.rvMovieComedy.visibility = View.VISIBLE
                adapterComedy.submitList(comedy.toMutableList())
            }

            dramaLiveData.observe(this@HomeActivity) { drama ->
                binding.rvMovieDrama.visibility = View.VISIBLE
                adapterDrama.submitList(drama.toMutableList())
            }

            adventureLiveData.observe(this@HomeActivity) { adventure ->
                binding.rvMovieAdventure.visibility = View.VISIBLE
                adapterAdventure.submitList(adventure.toMutableList())
            }
        }
    }

    override fun onClickMovie(movie: MovieEntity) {
        this.startActivity(MovieActivity.newInstance(this, movie))
    }
}
