package com.dariobrux.pokemon.app.common.extension

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dariobrux.pokemon.app.R

/**
 *
 * Created by Dario Bruzzese on 29/11/2020.
 *
 * This file contains all Glide (applied to ImageView) extended methods.
 *
 */

/**
 * Load an image url with Glide into the ImageView.
 * @param context the [Context] of the application.
 * @param url the image url.
 * @param onDominantColor an asynchronous callback that returns the dominant color of the image.
 */
fun ImageView.loadImage(context: Context, url: String, onDominantColor: (Int) -> Unit) {
    Glide.with(context).asBitmap().load(url).diskCacheStrategy(DiskCacheStrategy.DATA).listener(object : RequestListener<Bitmap> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
            return true
        }

        override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            resource ?: return true
            resource.getDominantColor(ContextCompat.getColor(context, R.color.white)) { color ->
                onDominantColor.invoke(color)
            }
            return false
        }
    }).into(this)
}

/**
 * From an image name converting into a drawable resource, load the drawable with Glide into the ImageView.
 * @param context the [Context] of the application.
 * @param name the image name into drawable folder.
 */
fun ImageView.loadImage(context: Context, name: String) {
    val res = context.resources.getIdentifier(name, "drawable", context.packageName)
    Glide.with(context).load(res).into(this)
}
