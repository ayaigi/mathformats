package com.ayaigi.mathformats

import kotlin.math.roundToInt

class MathFormats (value: Double, val type: FormatTypes) {
    private val valueCombi = ValueFormats(value)
    private val UnitsFractions = UnitsFractions(value)
    companion object {
        /** in m*/
        private const val AU = 149_597_870_700.0

        enum class FormatTypes {
            Degree,
            Time,
            Duration,
            Distance
        }
        internal class ValueFormats(val value: Double) {
            val millis: Double
                get() = (value * 1000.0)
            val milliSecs: Long
                get() = (value * (1000 * 3600)).toLong()
            val fullV: Double
                get() = (value)
        }
        /**
         * value as basic unit
         *
         * i.e.: hours, degrees, meters
         */
        fun asDegree(value: Double) = MathFormats(value, FormatTypes.Degree)
        /**
         * value as basic unit
         *
         * i.e.: hours, degrees, meters
         */
        fun asTime(value: Double) = MathFormats(value, FormatTypes.Time)
        /**
         * value as basic unit
         *
         * i.e.: hours, degrees, meters
         */
        fun asDuration(value: Double) = MathFormats(value, FormatTypes.Duration)
        /**
         * value as basic unit
         *
         * i.e.: hours, degrees, meters
         */
        fun asDistance(value: Double) = MathFormats(value, FormatTypes.Distance)
    }

    /**
     * @param deciLast true: Decimal-Part comes Last, false reverse, only in effect when both are present
     * @param precision Points of precision; 0 will not show it
     * @param units Number of Units; 0 will not show it
     * @param delimiter between the individual units
     * @param separator between the individual parts
     */
    fun smartFormat(deciLast: Boolean = true, precision: Int = 2, units: Int = 2, delimiter: String = " ", separator: String = " | "): String {
        if(type == FormatTypes.Distance){
            val au = valueCombi.fullV / AU
            val km = valueCombi.fullV / 1000.0
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
                else -> throw Exception("${valueCombi.fullV}")
            }
        }
        val sign = type != FormatTypes.Time
        fun dmms(): String{
            if(units <= 0) return ""
            return UnitsFractions.inParts.let {
                val last = if(units > it.size) it.size else units
                val l = it.subList(0, last)
                l.joinToString(delimiter) { it.format(sign) }
            }
        }
        fun deci(): String =
            if (precision <= 0) ""
            else UnitsFractions.inBestUnit.format(sign, precision)


        val deci = deci()
        val dmms = dmms()

        val separator = if(deci == "" || dmms == "") "" else separator

        val str =
            if(deciLast) "$dmms$separator$deci"
            else "$deci$separator$dmms"

        return if (type == FormatTypes.Degree) timeToDegree(str) else str
    }
    private fun timeToDegree(value: String) =
        value.replace(TimeUnit.TransformValues.HOURS.UnitSign[0], '°').replace(TimeUnit.TransformValues.MINUTES.UnitSign[0], '\'').replace(TimeUnit.TransformValues.SECONDS.UnitSign[0], '\"')

}