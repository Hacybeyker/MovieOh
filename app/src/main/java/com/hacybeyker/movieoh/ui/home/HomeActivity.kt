package com.hacybeyker.movieoh.ui.home

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import com.hacybeyker.movieoh.commons.base.BaseActivity
import com.hacybeyker.movieoh.databinding.ActivityHomeBinding
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.ui.OnItemMovie
import com.hacybeyker.movieoh.ui.home.adapter.ActionAdapter
import com.hacybeyker.movieoh.ui.home.adapter.AdventureAdapter
import com.hacybeyker.movieoh.ui.home.adapter.AnimationAdapter
import com.hacybeyker.movieoh.ui.home.adapter.ComedyAdapter
import com.hacybeyker.movieoh.ui.home.adapter.DramaAdapter
import com.hacybeyker.movieoh.ui.home.adapter.TrendingAdapter
import com.hacybeyker.movieoh.ui.home.adapter.UpcomingAdapter
import com.hacybeyker.movieoh.ui.home.viewmodel.HomeViewModel
import com.hacybeyker.movieoh.ui.movie.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), OnItemMovie {

    private val adapterUpcoming: UpcomingAdapter by lazy { UpcomingAdapter { onClickMovie(it) } }
    private val adapterTrending: TrendingAdapter by lazy { TrendingAdapter { onClickMovie(it) } }
    private val adapterAction: ActionAdapter by lazy { ActionAdapter { onClickMovie(it) } }
    private val adapterAnimation: AnimationAdapter by lazy { AnimationAdapter { onClickMovie(it) } }
    private val adapterComedy: ComedyAdapter by lazy { ComedyAdapter { onClickMovie(it) } }
    private val adapterDrama: DramaAdapter by lazy { DramaAdapter { onClickMovie(it) } }
    private val adapterAdventure: AdventureAdapter by lazy { AdventureAdapter { onClickMovie(it) } }

    override val viewBinding: ActivityHomeBinding
        get() = ActivityHomeBinding.inflate(layoutInflater)

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

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
        this.startActivity(DetailActivity.newInstance(this, movie))
    }
}
