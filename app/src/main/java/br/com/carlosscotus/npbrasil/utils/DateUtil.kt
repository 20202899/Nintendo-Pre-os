package br.com.carlosscotus.npbrasil.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatToString(from: DateUtil.DateFormat, to: DateUtil.DateFormat): String? {
    val locale = Locale("pt", "BR")
    val inFormat = SimpleDateFormat(
        from.value,
        locale
    )
    val outFormat = SimpleDateFormat(
        to.value,
        locale
    )

    return try {
        val date = inFormat.parse(this)
        date?.let { outFormat.format(it) }
    } catch (e: Exception) {
        return null
    }
}

object DateUtil {

    enum class DateFormat(val value: String) {
        FORMAT_DATE_API("yyyy-MM-dd'T'HH:mm:ss.sss'Z'"),
        FORMAT_DATE_UI("dd/MM/yyyy")
    }
}

