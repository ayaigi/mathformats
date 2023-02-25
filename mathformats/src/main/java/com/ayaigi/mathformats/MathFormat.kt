package com.ayaigi.mathformats

import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.logging.Level
import kotlin.math.roundToInt

/**
 * value as basic unit
 *
 * i.e.: hours, degrees, meters
 */
class MathFormat(value: Double, val type: FormatTypes) {
    private val value = ValueFormats(value)

    companion object {
        /** in m*/
        private const val AU = 149_597_870_700.0

        enum class FormatTypes {
            Degree,
            Time,
            Duration,
            Distance
        }
        /**
         * value as basic unit
         *
         * i.e.: hours, degrees, meters
         */
        fun asDegree(value: Double) = MathFormat(value, FormatTypes.Degree)
        /**
         * value as basic unit
         *
         * i.e.: hours, degrees, meters
         */
        fun asTime(value: Double) = MathFormat(value, FormatTypes.Time)
        /**
         * value as basic unit
         *
         * i.e.: hours, degrees, meters
         */
        fun asDuration(value: Double) = MathFormat(value, FormatTypes.Duration)
        /**
         * value as basic unit
         *
         * i.e.: hours, degrees, meters
         */
        fun asDistance(value: Double) = MathFormat(value, FormatTypes.Distance)
        private class ValueFormats(val value: Double) {
            val millis: Double
                get() = (value / 1000.0)
            val milliSecs: Long
                get() = (value * (1000 * 3600)).toLong()
            val fullV: Double
                get() = (value)
        }

        internal val chronoUnits = ChronoUnit.values().toList().toMutableList().also {
            it.remove(ChronoUnit.FOREVER)
            it.remove(ChronoUnit.HALF_DAYS)
            it.remove(ChronoUnit.WEEKS)
            it.remove(ChronoUnit.ERAS)
            it.reverse()
        }.toList()
    }

    internal fun time_int(): List<TimeUnits.TimeUnit> {
        val start = TimeUnits.warp(TimeUnits.Hours(value.fullV), ChronoUnit.MILLENNIA)
        val list = mutableListOf<TimeUnits.TimeUnit>()
        var unit = start
        var int: Int
        for (i in chronoUnits){
            int = unit.transform.toInt()
            unit = TimeUnits.fromId(unit.id, (unit.transform - int))
            list.add(TimeUnits.fromId(unit.id, int.toDouble()))
            unit = unit.down()
        }
        return list.toList()
    }
    private fun time_int_units(units: Int): List<TimeUnits.TimeUnit> {
        val list = time_int().toMutableList()
        var l0: Int = 0
        for (i in list.indices){
            if(list[i].toString(false)[0] == '0') l0 = i
            else break
        }
        val end = (l0+units+1).let {
            if(it > list.indices.last) list.indices.last else it
        }
        val l = list.subList((l0+1), (end))
        return l
    }

    fun FormatUnitsAndCompact(units: Int, delimiter: String = "", precision: Int = 3): String {
        return ""
    }

    internal fun OneUnit(unit: Boolean = true){
        val start = TimeUnits.warp(TimeUnits.Hours(value.fullV), ChronoUnit.MILLENNIA)
        val list = mutableListOf<TimeUnits.TimeUnit>()
        var unit = start
        var int: Int
        for (i in chronoUnits){
            int = unit.transform.toInt()
            unit = TimeUnits.fromId(unit.id, (unit.transform - int))
            list.add(TimeUnits.fromId(unit.id, int.toDouble()))
            unit = unit.down()
        }
        println(list)
    }

    fun format_for_units(units: Int, delimiter: String): String {
        if(check0()) return "0"
        fun wTime(): String {
            true
            val l = time_int_units(units).joinToString(delimiter) {
                it.toString(false)
            }
            return l
        }
        fun wDuration(): String {
            return time_int_units(units).joinToString(delimiter) { it.toString(true) }
        }
        fun wDegree() = timeToDegree(wDuration())

        fun wDistance(): String {
            val au = value.fullV / AU
            val km = value.fullV / 1000.0
            return when {
                au < 0.1 -> {
                    km.roundToInt().toString() + "km"
                }
                au < 1.0 -> {
                    "0." + (au * 100).roundToInt().toString() + "AU"
                }
                au >= 10 -> {
                    (au.roundToInt()).toString() + "AU"
                }
                au >= 1.0 -> {
                    (au * 10).roundToInt().toString().toCharArray().joinToString(".") + "AU"
                }
                else -> throw Exception("${value.fullV}")
            }
        }
        return when (type) {
            FormatTypes.Degree -> wDegree()
            FormatTypes.Time -> wTime()
            FormatTypes.Duration -> wDuration()
            FormatTypes.Distance -> wDistance()
        }
    }

    @Deprecated(message = "")
    private fun smart_time_unit(): ChronoUnit {
        val d = Duration.ofMillis(value.milliSecs)
        var t: Long
        var bu: ChronoUnit = ChronoUnit.NANOS
        for (i in chronoUnits) {
            if (d.get(i) == 0L) continue
            else {
                bu = i
                break
            }
        }
        return bu
    }

    private fun timeToDegree(value: String) =
        value.replace(TimeUnits.Hours().short[0], '°').replace(TimeUnits.Minutes().short[0], '\'').replace(TimeUnits.Seconds().short[0], '\"')

    private fun check0() = (value.milliSecs == 0L)

}