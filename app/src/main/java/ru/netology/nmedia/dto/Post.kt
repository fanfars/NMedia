package ru.netology.nmedia.dto

import java.math.RoundingMode
import java.text.DecimalFormat

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 0,
    var shares: Int = 0,
    var views: Int = 0,
    var likedByMe: Boolean = false
)

fun countFormat(count: Int): String {
    return when (count) {
        in 1..999 -> "${count.toString()}"
        in 1000..1099 ->"${roundNoDecimal(count.toDouble()/1_000.0)}K"
        in 1100..9_999 ->"${roundWithDecimal(count.toDouble()/1_000.0)}K"
        in 10_000..999_999 ->"${roundNoDecimal(count.toDouble()/1_000.0)}K"
        in 1_000_000..1_099_999 ->"${roundNoDecimal(count.toDouble()/1_000_000.0)}M"
        in 1_100_000..Int.MAX_VALUE ->"${roundWithDecimal(count.toDouble()/1_000_000.0)}M"

        else-> count.toString()
    }
}

fun roundWithDecimal(number: Double): Double? {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(number).toDouble()
}
fun roundNoDecimal(number: Double): Int? {
    val df = DecimalFormat("#")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(number).toInt()
}