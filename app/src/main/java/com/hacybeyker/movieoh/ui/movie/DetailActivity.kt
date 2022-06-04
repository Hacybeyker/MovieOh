package com.hacybeyker.movieoh.ui.movie

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.chip.Chip
import com.hacybeyker.movieoh.R
import com.hacybeyker.movieoh.commons.base.BaseActivity
import com.hacybeyker.movieoh.databinding.ActivityDetailBinding
import com.hacybeyker.movieoh.domain.entity.GenreEntity
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.entity.StreamEntity
import com.hacybeyker.movieoh.ui.OnItemMovie
import com.hacybeyker.movieoh.ui.movie.adapter.CastAdapter
import com.hacybeyker.movieoh.ui.movie.adapter.SimilarAdapter
import com.hacybeyker.movieoh.ui.movie.viewmodel.DetailViewModel
import com.hacybeyker.movieoh.utils.extensions.format
import com.hacybeyker.movieoh.utils.extensions.getRuntime
import com.hacybeyker.movieoh.utils.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(), OnItemMovie {

    private val similarAdapter: SimilarAdapter by lazy { SimilarAdapter(this) }
    private val castAdapter: CastAdapter by lazy { CastAdapter() }

    override val viewBinding: ActivityDetailBinding
        get() = ActivityDetailBinding.inflate(layoutInflater)

    override val viewModelClass: Class<DetailViewModel>
        get() = DetailViewModel::class.java

    private var movie: MovieEntity? = null

    companion object {
        fun newInstance(activity: Activity, movie: MovieEntity): Intent {
            val intent = Intent(activity, DetailActivity::class.java)
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
        movie?.let {
            viewModel.init(it.id)
        }
    }

    override fun launchObservers() {
        viewModel.movieLiveData.observe(this) { movie ->
            this.movie = movie
            showExtraInfo()
        }

        viewModel.creditsLiveData.observe(this) { credits ->
            binding.rvMovieCast.visibility = View.VISIBLE
            castAdapter.submitList(credits.cast.toMutableList())
        }

        viewModel.similarLiveData.observe(this) { similar ->
            binding.rvMovieSimilar.visibility = View.VISIBLE
            similarAdapter.submitList(similar.toMutableList())
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

    private fun showExtraInfo() {
        movie?.let { movie ->
            binding.apply {
                includeInfo.tvTextText.text = movie.runtime.getRuntime()
                generateChipGenre(movie.genres)
                if (movie.stream != null && movie.stream != StreamEntity.NONE) {
                    includeInfo.vDividerText.visibility = View.VISIBLE
                    includeInfo.clContainerIcon.visibility = View.VISIBLE
                    includeInfo.ivIconIcon.setImageResource(movie.stream!!.icon)
                    includeInfo.clContainerIcon.setOnClickListener {
                        openLink(movie.homepage)
                    }
                }
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

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onClickMovie(movie: MovieEntity) {
        this.startActivity(newInstance(this, movie))
    }
}
