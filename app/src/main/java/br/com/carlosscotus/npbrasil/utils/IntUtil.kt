package br.com.carlosscotus.npbrasil.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

object IntUtil {

    fun Int.toDP(context: Context): Int {
        val r: Resources = context.resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            r.displayMetrics
        )
        return px.toInt()
    }
}