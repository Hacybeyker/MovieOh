package com.hacybeyker.uikit.component.section

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.hacybeyker.uikit.R
import com.hacybeyker.uikit.databinding.HeaderBinding

class Header @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: HeaderBinding by lazy {
        HeaderBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var textValue = ""
        set(value) {
            field = value
            setText(value)
        }

    private fun setText(value: String) {
        binding.tvTitleHeader.text = value
    }

    private var iconValue = 0
        set(value) {
            field = value
            setIcon(value)
        }

    private fun setIcon(value: Int) {
        binding.ivIconHeader.setImageResource(value)
    }

    /*private var colorTextValue = 0
        set(value) {
            field = value
            setColorText(value)
        }

    private fun setColorText(value: Int) {
        binding.tvTitleHeader.setTextColor(value)
    }

    private var colorIconValue = 0
        set(value) {
            field = value
            setColorIcon(value)
        }

    private fun setColorIcon(value: Int) {
        binding.ivIconHeader.setColorFilter(value)
    }*/

    private var showIconValue = true
        set(value) {
            field = value
            showIcon(value)
        }

    private fun showIcon(value: Boolean) {
        binding.ivIconHeader.visibility = if (value) VISIBLE else GONE
    }

    init {
        loadAttributes(context, attrs)
    }

    private fun loadAttributes(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val attributeSet =
                context.obtainStyledAttributes(it, R.styleable.Header)
            attributeSet.let { typedArray ->
                textValue = typedArray.getString(R.styleable.Header_text) ?: ""
                showIconValue = typedArray.getBoolean(R.styleable.Header_showIcon, true)
                iconValue = typedArray.getResourceId(
                    R.styleable.Header_icon,
                    R.drawable.icon_arrow_right
                )
                /*colorTextValue =
                    typedArray.getInt(
                        R.styleable.Header_colorText,
                        context.getColor(R.color.silver_sand)
                    )
                colorIconValue =
                    typedArray.getInt(
                        R.styleable.Header_colorIcon,
                        context.getColor(R.color.philippine_gray)
                    )*/
            }
            attributeSet.recycle()
        }
    }
}