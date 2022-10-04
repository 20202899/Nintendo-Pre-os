package br.com.carlosscotus.npbrasil.framework.imageloader

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

interface ImageLoader {

    fun load(from: ImageView?, url: String, listener: RequestListener<Bitmap>? = null)
}

class ImageLoaderImpl @Inject constructor(): ImageLoader {

    override fun load(from: ImageView?, url: String, listener: RequestListener<Bitmap>?) {
        from?.let { imageView ->
            Glide.with(imageView)
                .asBitmap()
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .load(url)
                .listener(listener)
                .into(imageView)
        }
    }
}