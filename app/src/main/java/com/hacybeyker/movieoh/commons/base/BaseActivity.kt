package com.hacybeyker.movieoh.commons.base

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    protected abstract val viewBinding: VB

    protected abstract val viewModelClass: Class<VM>

    open fun setupView() { /*Your implementation*/
    }

    open fun setupObservers() { /*Your implementation*/
    }

    open fun launchObservers() { /*Your implementation*/
    }

    @VisibleForTesting
    lateinit var viewModel: VM

    @VisibleForTesting
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[viewModelClass]
        binding = viewBinding
        setContentView(binding.root)
        setupView()
        setupObservers()
        launchObservers()
    }
}
