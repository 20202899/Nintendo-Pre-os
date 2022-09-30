package br.com.carlosscotus.npbrasil.framework.imageloader

import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

interface ImageLoader {
    fun load(from: ImageView?, url: String)
}

class ImageLoaderImpl @Inject constructor(): ImageLoader {

    override fun load(from: ImageView?, url: String) {
        from?.let { imageView ->
            Glide.with(imageView)
                .load(url)
                .into(imageView)
        }
    }
}