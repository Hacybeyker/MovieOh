package com.hacybeyker.movieoh.ui.movie

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.chip.Chip
import com.hacybeyker.movieoh.R
import com.hacybeyker.movieoh.commons.base.BaseActivity
import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.databinding.ActivityMovieBinding
import com.hacybeyker.movieoh.domain.entity.GenreEntity
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity
import com.hacybeyker.movieoh.domain.entity.StreamEntity
import com.hacybeyker.movieoh.ui.OnItemMovie
import com.hacybeyker.movieoh.ui.movie.adapter.CastAdapter
import com.hacybeyker.movieoh.ui.movie.adapter.SimilarAdapter
import com.hacybeyker.movieoh.ui.movie.viewmodel.MovieViewModel
import com.hacybeyker.movieoh.utils.extensions.format
import com.hacybeyker.movieoh.utils.extensions.getRuntime
import com.hacybeyker.movieoh.utils.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : BaseActivity<ActivityMovieBinding, MovieViewModel>(), OnItemMovie {

    private val similarAdapter: SimilarAdapter by lazy { SimilarAdapter(onClick = { onClickMovie(it) }) }
    private val castAdapter: CastAdapter by lazy { CastAdapter() }

    override val viewBinding: ActivityMovieBinding
        get() = ActivityMovieBinding.inflate(layoutInflater)

    override val viewModelClass: Class<MovieViewModel>
        get() = MovieViewModel::class.java

    private var movie: MovieEntity? = null

    companion object {
        fun newInstance(activity: Activity, movie: MovieEntity): Intent {
            val intent = Intent(activity, MovieActivity::class.java)
            val bundle = Bundle().apply { putParcelable(MovieEntity::class.java.name, movie) }
            intent.putExtras(bundle)
            return intent
        }
    }

    override fun getIntentData() {
        intent.let {
            val movieData = it.extras?.getParcelable<MovieEntity>(MovieEntity::class.java.name)
            this.movie = movieData as MovieEntity
        }
    }

    override fun setupView() {
        binding.rvMovieSimilar.adapter = similarAdapter
        binding.rvMovieSimilar.setHasFixedSize(true)
        binding.rvMovieSimilar.itemAnimator = DefaultItemAnimator()

        binding.rvMovieCast.adapter = castAdapter
        binding.rvMovieCast.setHasFixedSize(true)
        binding.rvMovieCast.itemAnimator = DefaultItemAnimator()

        binding.ivBack.setOnClickListener { onBackPressed() }
        showMovieData()
    }

    override fun setupObservers() {
        movie?.let { movieEntity ->
            viewModel.init(movieEntity)
        }
    }

    override fun launchObservers() {
        viewModel.apply {
            loadingLiveData.observe(this@MovieActivity) { loading ->
                when (loading) {
                    true -> {
                        binding.sflContainerMovie.visibility = View.VISIBLE
                        binding.nsvMainDetailScroll.visibility = View.GONE
                        binding.sflContainerMovie.startShimmer()
                    }

                    else -> {
                        binding.sflContainerMovie.visibility = View.GONE
                        binding.nsvMainDetailScroll.visibility = View.VISIBLE
                        binding.sflContainerMovie.stopShimmer()
                    }
                }
            }

            movieLiveData.observe(this@MovieActivity) { movie ->
                this@MovieActivity.movie = movie
                setupExtraInfo()
            }

            creditsLiveData.observe(this@MovieActivity) { credits ->
                binding.rvMovieCast.visibility = View.VISIBLE
                castAdapter.submitList(credits.cast.toMutableList())
            }

            similarLiveData.observe(this@MovieActivity) { similar ->
                binding.rvMovieSimilar.visibility = View.VISIBLE
                similarAdapter.submitList(similar.toMutableList())
            }

            platforms.observe(this@MovieActivity) { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        Toast.makeText(
                            this@MovieActivity,
                            "Here - ${result.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is NetworkResult.Loading -> {
                        binding.hPlatforms.visibility = View.GONE
                        binding.hsvPlatforms.visibility = View.GONE
                    }

                    is NetworkResult.Success -> {
                        result.data?.let {
                            if (it.isNotEmpty()) {
                                binding.hPlatforms.visibility = View.VISIBLE
                                binding.hsvPlatforms.visibility = View.VISIBLE
                                generateChipPlatforms(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showMovieData() {
        movie?.let { movie ->
            binding.apply {
                ivPosterLandscape.loadImage(movie.backdropPath)
                ivPosterPortrait.loadImage(movie.posterPath)
                tvTitle.text = movie.title
                tvReleaseDate.text = movie.releaseDate
                tvOverview.text = movie.overview
                includeInfo.tvTextIconText.text = movie.voteAverage.format(1)
                includeInfo.tvTextIconDescription.text =
                    String.format(getString(R.string.opinions), movie.voteCount)
            }
        }
    }

    private fun setupExtraInfo() {
        movie?.let { movie ->
            binding.apply {
                includeInfo.tvTextText.text = movie.runtime.getRuntime()
                generateChipGenre(movie.genres)
                showStream(movie)
            }
        }
    }

    private fun showStream(movie: MovieEntity) {
        movie.stream?.let { stream ->
            if (stream != StreamEntity.NONE) {
                binding.includeInfo.vDividerText.visibility = View.VISIBLE
                binding.includeInfo.clContainerIcon.visibility = View.VISIBLE
                binding.includeInfo.ivIconIcon.setImageResource(stream.icon)
                binding.includeInfo.clContainerIcon.setOnClickListener { openLink(movie.homepage) }
            }
        }
    }

    private fun generateChipGenre(list: List<GenreEntity>) {
        list.forEach { genre ->
            val chip = Chip(this)
            chip.text = genre.name
            chip.id = genre.id
            binding.cgGenre.addView(chip)
        }
    }

    private fun generateChipPlatforms(list: List<PlatformsEntity>) {
        list.forEach { platform ->
            val chip = Chip(this)
            chip.text = platform.name
            chip.setOnClickListener { openLink(platform.url) }
            binding.cgPlatforms.addView(chip)
        }
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onClickMovie(movie: MovieEntity) {
        this.startActivity(newInstance(this, movie))
    }
}
