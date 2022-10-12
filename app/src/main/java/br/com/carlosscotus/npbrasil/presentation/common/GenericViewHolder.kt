package br.com.carlosscotus.npbrasil.presentation.common

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class GenericViewHolder<T>(
    itemBinding: ViewBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    protected val resources: Resources = itemView.context.resources

    abstract fun bind(data: T)

    @SuppressLint("RestrictedApi")
    fun showPopMenu(@MenuRes menu: Int, view: View) {
        val popup = PopupMenu(itemView.context, view)
        popup.inflate(menu)

        if (popup.menu is MenuBuilder) {
            val menuBuilder = popup.menu as MenuBuilder
            menuBuilder.setOptionalIconsVisible(true)
            for (item in menuBuilder.visibleItems) {
                val iconMarginPx =
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 4.toFloat(), resources.displayMetrics)
                        .toInt()
                if (item.icon != null) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                        item.icon = InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx,0)
                    } else {
                        item.icon =
                            object : InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0) {
                                override fun getIntrinsicWidth(): Int {
                                    return intrinsicHeight + iconMarginPx + iconMarginPx
                                }
                            }
                    }
                }
            }
        }

        popup.show()
    }
}