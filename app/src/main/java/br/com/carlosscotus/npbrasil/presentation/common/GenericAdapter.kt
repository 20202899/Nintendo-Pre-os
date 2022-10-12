package br.com.carlosscotus.npbrasil.presentation.common

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

inline fun <T : ListItem, VH : GenericViewHolder<T>> getGenericAdapterOf(
    crossinline createViewHolder: (ViewGroup, Int) -> VH
): ListAdapter<T, VH> {

    return object : ListAdapter<T, VH>(GenericDiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return createViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(getItem(position))
        }
    }
}