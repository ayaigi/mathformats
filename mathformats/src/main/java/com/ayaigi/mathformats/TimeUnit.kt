package com.ayaigi.mathformats

import java.time.temporal.ChronoUnit

internal object TimeUnit {
    enum class TransformValues(
        val id: Int,
        val inHours: Double,
        val ChronoUnit: ChronoUnit,
        val UnitSign: String
    ) {
        YEARS(2, 365.0 * 24.0, ChronoUnit.YEARS, "a"),
        DAYS(1, 24.0, ChronoUnit.DAYS, "d"),
        HOURS(0, 1.0, ChronoUnit.HOURS, "h"),
        MINUTES(-1, 1.0 / 60.0, ChronoUnit.MINUTES, "M"),
        SECONDS(-2, MINUTES.inHours / 60.0, ChronoUnit.SECONDS, "s"),
        MILLIS(-3, SECONDS.inHours / 1000.0, ChronoUnit.MILLIS, "m"),
        MICROS(-4, MILLIS.inHours / 1000.0, ChronoUnit.MICROS, "µ"),
        NANOS(-5, MICROS.inHours / 1000.0, ChronoUnit.NANOS, "n");

        fun findById(id: Int) = findUnitById(id)
    }

    fun findUnitById(id: Int) = unitsSortById.let {
        it[it.binarySearch { it.id.compareTo(id) }]
    }

    val unitsSortById = TransformValues.values().toList().sortedBy { it.id }
    val unitsSortByIdDesc = unitsSortById.reversed()

    fun transformValue(from: TransformValues, to: TransformValues): Double {
        return from.inHours * (1.0 / to.inHours)
    }

    fun transform(value: Double, from: TransformValues, to: TransformValues) =
        value * transformValue(from, to)


}

fun main() {
    val l = TimeUnit.findUnitById(2)
    println(l)
}