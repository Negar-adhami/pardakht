package com.rhyme.modiriathesab

object FaNum {
    private val faNumbers = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")
    fun convert(text: String): String {
        if (text.isEmpty()) {
            return ""
        }
        var out = ""
        val length = text.length
        for (i in 0 until length) {
            val c = text[i]
            if (c in '0'..'9') {
                val number = c.toString().toInt()
                out += faNumbers[number]
            } else if (c == '٫' || c == ',') {
                out += '،'
            } else {
                out += c
            }
        }
        return out
    }
}