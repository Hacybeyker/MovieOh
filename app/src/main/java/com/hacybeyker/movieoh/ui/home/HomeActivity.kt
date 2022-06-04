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

    private val adapterTrending: TrendingAdapter by lazy { TrendingAdapter(this) }
    private val adapterUpcoming: UpcomingAdapter by lazy { UpcomingAdapter(this) }
    private val adapterAction: ActionAdapter by lazy { ActionAdapter(this) }
    private val adapterAnimation: AnimationAdapter by lazy { AnimationAdapter(this) }
    private val adapterComedy: ComedyAdapter by lazy { ComedyAdapter(this) }
    private val adapterDrama: DramaAdapter by lazy { DramaAdapter(this) }
    private val adapterAdventure: AdventureAdapter by lazy { AdventureAdapter(this) }

    override val viewBinding: ActivityHomeBinding
        get() = ActivityHomeBinding.inflate(layoutInflater)

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun setupView() {
        binding.rvMovieTrending.adapter = adapterTrending
        binding.rvMovieTrending.setHasFixedSize(true)
        binding.rvMovieTrending.itemAnimator = DefaultItemAnimator()

        binding.rvMovieUpcoming.adapter = adapterUpcoming
        binding.rvMovieUpcoming.setHasFixedSize(true)
        binding.rvMovieUpcoming.itemAnimator = DefaultItemAnimator()

        binding.rvMovieAction.adapter = adapterAction
        binding.rvMovieAction.setHasFixedSize(true)
        binding.rvMovieAction.itemAnimator = DefaultItemAnimator()

        binding.rvMovieAnimation.adapter = adapterAnimation
        binding.rvMovieAnimation.setHasFixedSize(true)
        binding.rvMovieAnimation.itemAnimator = DefaultItemAnimator()

        binding.rvMovieComedy.adapter = adapterComedy
        binding.rvMovieComedy.setHasFixedSize(true)
        binding.rvMovieComedy.itemAnimator = DefaultItemAnimator()

        binding.rvMovieDrama.adapter = adapterDrama
        binding.rvMovieDrama.setHasFixedSize(true)
        binding.rvMovieDrama.itemAnimator = DefaultItemAnimator()

        binding.rvMovieAdventure.adapter = adapterAdventure
        binding.rvMovieAdventure.setHasFixedSize(true)
        binding.rvMovieAdventure.itemAnimator = DefaultItemAnimator()
    }

    override fun launchObservers() {
        viewModel.initHome()
    }

    override fun setupObservers() {
        viewModel.trendingLiveData.observe(this) { trendingMovie ->
            binding.rvMovieTrending.visibility = View.VISIBLE
            adapterTrending.submitList(trendingMovie.toMutableList())
        }

        viewModel.upcomingLiveData.observe(this) { upcoming ->
            binding.rvMovieUpcoming.visibility = View.VISIBLE
            adapterUpcoming.submitList(upcoming.toMutableList())
        }

        viewModel.actionLiveData.observe(this) { action ->
            binding.rvMovieAction.visibility = View.VISIBLE
            adapterAction.submitList(action.toMutableList())
        }

        viewModel.animationLiveData.observe(this) { animation ->
            binding.rvMovieAnimation.visibility = View.VISIBLE
            adapterAnimation.submitList(animation.toMutableList())
        }

        viewModel.comedyLiveData.observe(this) { comedy ->
            binding.rvMovieComedy.visibility = View.VISIBLE
            adapterComedy.submitList(comedy.toMutableList())
        }

        viewModel.dramaLiveData.observe(this) { drama ->
            binding.rvMovieDrama.visibility = View.VISIBLE
            adapterDrama.submitList(drama.toMutableList())
        }

        viewModel.adventureLiveData.observe(this) { adventure ->
            binding.rvMovieAdventure.visibility = View.VISIBLE
            adapterAdventure.submitList(adventure.toMutableList())
        }
    }

    override fun onClickMovie(movie: MovieEntity) {
        this.startActivity(DetailActivity.newInstance(this, movie))
    }
}
