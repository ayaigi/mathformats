package com.ayaigi.mathformats

import java.time.temporal.ChronoUnit


internal class TimeUnits {
    companion object {
        fun transform(value: Double, from: TimeUnit, to: TimeUnit): Double {
            val to = to
            var now = from
            while (to.id != now.id) {
                now = if (to.id < now.id) {
                    now.down()
                } else {
                    now.up()
                }
            }
            return now.transform * value
        }
        fun warp(from: TimeUnit, to: ChronoUnit): TimeUnit {
            val to = fromChrono(to)
            var now = from
            while (to.id != now.id) {
                now = if (to.id < now.id) {
                    now.down()
                } else {
                    now.up()
                }
            }
            return now
        }
        fun fromChrono(unit: ChronoUnit, value: Double = 1.0): TimeUnit {
            return when(unit){
                ChronoUnit.NANOS -> Nanos(value)
                ChronoUnit.MICROS -> Micros(value)
                ChronoUnit.MILLIS -> Millis(value)
                ChronoUnit.SECONDS -> Seconds(value)
                ChronoUnit.MINUTES -> Minutes(value)
                ChronoUnit.HOURS -> Hours(value)
                ChronoUnit.DAYS -> Days(value)
                ChronoUnit.MONTHS -> Months(value)
                ChronoUnit.YEARS -> Years(value)
                ChronoUnit.DECADES -> Decades(value)
                ChronoUnit.CENTURIES -> Centuries(value)
                ChronoUnit.MILLENNIA -> Millennia(value)
                else -> TODO()
            }
        }
        fun fromId(id: Int, value: Double): TimeUnit{
            return when(id){
                1 -> Nanos(value)
                2 -> Micros(value)
                3 -> Millis(value)
                4 -> Seconds(value)
                5 -> Minutes(value)
                6 -> Hours(value)
                7 -> Days(value)
                8 -> Months(value)
                9 -> Years(value)
                10 -> Decades(value)
                11 -> Centuries(value)
                12 -> Millennia(value)
                else -> TODO()
            }
        }

    }

    interface TimeUnit {
        val transform: Double
        val id: Int
        fun up(): TimeUnit
        fun down(): TimeUnit
        fun toString(sign: Boolean): String
        val short: String
    }

    data class Nanos(override val transform: Double = 1.0) : TimeUnit {
        override val id = 1 + 0

        override fun up(): TimeUnit = TimeUnits.Centuries(transform * 10.0)

        override fun down() = TimeUnits.Nanos(transform)

        override val short: String = "n"

        override fun toString(sign: Boolean): String {
            val short = if (sign) short else ""
            return if(transform - transform.toInt() < 0.01) "${transform.toInt()}$short"
            else "${transform}$short"
        }
    }

    data class Micros(override val transform: Double = 1.0) : TimeUnit {
        override val id = 2

        override fun up(): TimeUnit = TimeUnits.Millis(transform / 1000.0)

        override fun down() = TimeUnits.Nanos(transform * 1000.0)

        override val short: String = "µ"

        override fun toString(sign: Boolean): String {
            val short = if (sign) short else ""
            return if(transform - transform.toInt() < 0.01) "${transform.toInt()}$short"
            else "${transform}$short"
        }
    }

    data class Millis(override val transform: Double = 1.0) : TimeUnit {
        override val id = 3

        override fun up(): TimeUnit = TimeUnits.Seconds(transform / 1000.0)

        override fun down() = TimeUnits.Micros(transform * 1000.0)

        override val short: String = "m"

        override fun toString(sign: Boolean): String {
            val short = if (sign) short else ""
            return if(transform - transform.toInt() < 0.01) "${transform.toInt()}$short"
            else "${transform}$short"
        }
    }

    data class Seconds(override val transform: Double = 1.0) : TimeUnit {
        override val id = 4

        override fun up(): TimeUnit = TimeUnits.Minutes(transform / 60.0)

        override fun down() = TimeUnits.Millis(transform * 1000.0)

        override val short: String = "s"

        override fun toString(sign: Boolean): String {
            val short = if (sign) short else ""
            return if(transform - transform.toInt() < 0.01) "${transform.toInt()}$short"
            else "${transform}$short"
        }
    }

    data class Minutes(override val transform: Double = 1.0) : TimeUnit {
        override val id = 5

        override fun up(): TimeUnit = TimeUnits.Hours(transform / 60.0)

        override fun down() = TimeUnits.Seconds(transform * 60.0)

        override val short: String = "M"

        override fun toString(sign: Boolean): String {
            val short = if (sign) short else ""
            return if(transform - transform.toInt() < 0.01) "${transform.toInt()}$short"
            else "${transform}$short"
        }
    }

    data class Hours(override val transform: Double = 1.0) : TimeUnit {
        override val id = 6

        override fun up(): TimeUnit = TimeUnits.Days(transform / 24.0)

        override fun down() = TimeUnits.Minutes(transform * 60.0)

        override val short: String = "h"

        override fun toString(sign: Boolean): String {
            val short = if (sign) short else ""
            return if(transform - transform.toInt() < 0.01) "${transform.toInt()}$short"
            else "${transform}$short"
        }
    }

    data class Days(override val transform: Double = 1.0) : TimeUnit {
        override val id = 7

        override fun up(): TimeUnit = TimeUnits.Months(transform / 30.0)

        override fun down() = TimeUnits.Hours(transform * 24.0)

        override val short: String = "d"

        override fun toString(sign: Boolean): String {
            val short = if (sign) short else ""
            return if(transform - transform.toInt() < 0.01) "${transform.toInt()}$short"
            else "${transform}$short"
        }
    }

    data class Months(override val transform: Double = 1.0) : TimeUnit {
        override val id = 8

        override fun up(): TimeUnit = TimeUnits.Years(transform / 12.0)

        override fun down() = TimeUnits.Days(transform * 30.0)

        override val short: String = "M"

        override fun toString(sign: Boolean): String {
            val short = if (sign) short else ""
            return if(transform - transform.toInt() < 0.01) "${transform.toInt()}$short"
            else "${transform}$short"
        }
    }

    data class Years(override val transform: Double = 1.0) : TimeUnit {
        override val id = 9

        override fun up(): TimeUnit = TimeUnits.Decades(transform / 10.0)

        override fun down() = TimeUnits.Months(transform * 12.0)

        override val short: String = "Y"

        override fun toString(sign: Boolean): String {
            val short = if (sign) short else ""
            return if(transform - transform.toInt() < 0.01) "${transform.toInt()}$short"
            else "${transform}$short"
        }
    }

    data class Decades(override val transform: Double = 1.0) : TimeUnit {
        override val id = 10

        override fun up(): TimeUnit = TimeUnits.Centuries(transform / 10.0)

        override fun down() = TimeUnits.Years(transform * 10.0)

        override val short: String = "D"

        override fun toString(sign: Boolean): String {
            val short = if (sign) short else ""
            return if(transform - transform.toInt() < 0.01) "${transform.toInt()}$short"
            else "${transform}$short"
        }
    }

    data class Centuries(override val transform: Double = 1.0) : TimeUnit {
        override val id = 11

        override fun up(): TimeUnit = TimeUnits.Millennia(transform / 10.0)

        override fun down() = TimeUnits.Decades(transform * 10.0)

        override val short: String = "C"

        override fun toString(sign: Boolean): String {
            val short = if (sign) short else ""
            return if(transform - transform.toInt() < 0.01) "${transform.toInt()}$short"
            else "${transform}$short"
        }
    }

    data class Millennia(override val transform: Double = 1.0) : TimeUnit {
        override val id = 12

        override fun up(): TimeUnit = TimeUnits.Millennia(transform)

        override fun down() = TimeUnits.Centuries(transform * 10.0)

        override val short: String = "a"

        override fun toString(sign: Boolean): String {
            val short = if (sign) short else ""
            return if(transform - transform.toInt() < 0.01) "${transform.toInt()}$short"
            else "${transform}$short"
        }
    }
}