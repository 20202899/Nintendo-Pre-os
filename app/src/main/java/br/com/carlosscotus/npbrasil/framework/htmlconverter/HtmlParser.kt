package br.com.carlosscotus.npbrasil.framework.htmlconverter

interface HtmlParser<in P, out T> {
    suspend fun converter(string: P): T
}