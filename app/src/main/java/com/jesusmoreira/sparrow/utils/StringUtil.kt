package com.jesusmoreira.sparrow.utils

object StringUtil {
    fun printObject(t: Any): String? {
        val sb = StringBuilder()
        t.javaClass.declaredFields.forEach { field ->
            field.isAccessible = true
            try {
                sb.append(field.name).append(": ").append(field[t]).append('\n')
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return sb.toString()
    }
}