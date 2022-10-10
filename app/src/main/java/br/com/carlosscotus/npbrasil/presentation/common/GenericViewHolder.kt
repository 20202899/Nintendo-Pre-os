package br.com.carlosscotus.npbrasil.presentation.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

inline fun <T : ListItem, VH : GenericViewHolder<T>> getGenericAdapterOf(
    crossinline createViewHolder: (ViewGroup) -> VH
): ListAdapter<T, VH> {
    return object : ListAdapter<T, VH>(GenericDiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return createViewHolder(parent)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(getItem(position))
        }
    }
}

interface ListItem {
    val key: String

    fun areItemsTheSame(other: ListItem) = this.key == other.key

    fun areContentsTheSame(other: ListItem) = this == other
}

abstract class GenericViewHolder<T>(
    itemBinding: ViewBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    abstract fun bind(data: T)
}

class GenericDiffCallback<T : ListItem> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}