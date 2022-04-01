package com.hacybeyker.movieoh.commons.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<D : ViewBinding, V : ViewModel> : AppCompatActivity() {

    protected abstract val viewBinding: D

    protected abstract val viewModelClass: Class<V>

    abstract fun setupView()

    abstract fun setupObservers()

    open fun startObserver(){}

    internal lateinit var viewModel: V
        private set

    internal lateinit var binding: D
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[viewModelClass]
        binding = viewBinding
        setContentView(binding.root)
        setupView()
        setupObservers()
    }

    /*private fun getViewModelClass(): Class<V> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1]
        return type as Class<V>
    }*/

}
