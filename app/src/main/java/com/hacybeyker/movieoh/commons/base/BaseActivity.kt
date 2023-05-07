package com.hacybeyker.movieoh.commons.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    protected abstract val viewBinding: VB

    protected abstract val viewModelClass: Class<VM>

    protected abstract fun setupView()

    protected abstract fun setupObservers()

    protected abstract fun launchObservers()

    open fun setupStatusBar() = Unit

    open fun getIntentData() = Unit

    lateinit var viewModel: VM

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[viewModelClass]
        binding = viewBinding
        setContentView(binding.root)
        setupStatusBar()
        getIntentData()
        setupView()
        setupObservers()
        launchObservers()
    }
}
