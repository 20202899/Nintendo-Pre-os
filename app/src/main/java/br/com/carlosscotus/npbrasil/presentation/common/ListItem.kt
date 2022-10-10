package br.com.carlosscotus.npbrasil.presentation.common

interface ListItem {

    val key: String

    fun areItemsTheSame(other: ListItem) = this.key == other.key

    fun areContentsTheSame(other: ListItem) = this == other
}