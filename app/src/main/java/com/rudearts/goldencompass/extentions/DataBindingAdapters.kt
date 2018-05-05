package com.rudearts.goldencompass.extentions

import android.databinding.BindingAdapter
import android.widget.ImageView

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}
