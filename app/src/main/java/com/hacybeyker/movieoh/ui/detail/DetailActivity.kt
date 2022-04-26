package com.hacybeyker.movieoh.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import com.hacybeyker.movieoh.commons.base.BaseActivity
import com.hacybeyker.movieoh.databinding.ActivityDetailBinding
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.ui.OnItemMovie
import com.hacybeyker.movieoh.ui.detail.adapter.SimilarAdapter
import com.hacybeyker.movieoh.ui.detail.viewmodel.DetailViewModel
import com.hacybeyker.movieoh.utils.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(), OnItemMovie {

    private val similarAdapter: SimilarAdapter by lazy { SimilarAdapter(this) }

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
        binding.ivBack.setOnClickListener { onBackPressed() }
        showMovieData()
    }

    override fun setupObservers() {
        movie?.let {
            viewModel.fetchSimilar(it.id)
        }
    }

    override fun launchObservers() {
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
            }
        }
    }

    override fun onClickMovie(movie: MovieEntity) {
        this.startActivity(newInstance(this, movie))
    }
}
