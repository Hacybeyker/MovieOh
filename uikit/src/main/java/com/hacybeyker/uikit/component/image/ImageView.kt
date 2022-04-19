package com.hacybeyker.uikit.component.image

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.hacybeyker.uikit.R

class ImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShapeableImageView(context, attrs, defStyleAttr) {

    private var percentRounded = 0
        set(value) {
            field = value
            setPercentValue(value)
        }

    private fun setPercentValue(value: Int) {
        this.shapeAppearanceModel = this.shapeAppearanceModel
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, value.toFloat())
            .build()
    }

    init {
        loadAttributes(context, attrs)
    }

    private fun loadAttributes(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val attributeSet = context.obtainStyledAttributes(it, R.styleable.ImageView)
            attributeSet.let { typedArray ->
                percentRounded =
                    typedArray.getDimensionPixelSize(R.styleable.ImageView_percentRounded, 0)
            }
            attributeSet.recycle()
        }
    }

}