package com.ayaigi.mathformats

import java.util.Locale

/**
 * Hours / Degrees / Meters
 */
internal class UnitsFractions(val value: Double) {
    val Hours = TimeUnit.TransformValues.HOURS
    val UnitList = TimeUnit.unitsSortByIdDesc
    val inUnits = run {
        val unitList = UnitList
        val inUnits =
            unitList.map {
                TimeValue(
                    TimeUnit.transform(
                        value,
                        TimeUnit.TransformValues.HOURS,
                        it
                    ), it
                )
            }
        //println(inUnits)
        inUnits
    }
    val bestUnit = run {
        var bu = TimeUnit.TransformValues.HOURS
        for (v in inUnits) {
            if (v.value >= 1.0) {
                bu = v.unit
                break
            }
        }
        bu
    }
    val inBestUnit = run {
        val inUnit = TimeUnit.transform(value, Hours, bestUnit)
        TimeValue(inUnit, bestUnit)
    }
    val unitListStartBest = run {
        TimeUnit.unitsSortByIdDesc.let {
            val ix = it.indexOf(bestUnit)
            it.subList(ix, it.size)
        }
    }
    val inParts = run {
        val list = mutableListOf<TimeValue>()
        fun part(value: Double): Pair<Double, Double> {
            val int = value.toInt()
            val fr = value - int
            return Pair(int.toDouble(), fr)
        }

        var fraction = inBestUnit.value
        var lastInHour = inBestUnit.unit.inHours
        for (unit in unitListStartBest) {
            fraction *= lastInHour / unit.inHours
            part(fraction).let {
                fraction = it.second
                list.add(TimeValue(it.first, unit))
            }
            lastInHour = unit.inHours
        }
        list
    }
}

internal data class TimeValue(val value: Double, val unit: TimeUnit.TransformValues) {
    fun format(sign: Boolean, precision: Int = 2): String {
        val sign = if (sign) unit.UnitSign else ""
        val v = if (value - value.toInt() < 0.01) "${value.toInt()}" else "%.${precision}f".format(
            Locale.ENGLISH, value)
        return "$v$sign"
    }
}