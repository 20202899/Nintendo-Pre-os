package br.com.carlosscotus.npbrasil.utils

import java.util.*
import kotlin.math.ceil

object DoubleUtil {

    fun Double.formatCurrency(): String {
        return String.format(Locale("pt", "BR"), "R$ %.2f", this)
    }

    fun formatPercentageFromSubtraction(discount: Double, price: Double): String {
        val result = ceil((discount / price) * 100)
        return  String.format(Locale("pt", "BR"), "%.0f", result) + "%"
    }

    fun Double.formatPercentageCurrency(): String {
        return String.format(Locale("pt", "BR"), "%.0f", this) + "%"
    }
}