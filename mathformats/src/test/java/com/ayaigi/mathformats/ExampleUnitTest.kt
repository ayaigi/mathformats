package com.ayaigi.mathformats

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    fun addition_isCorrect() {

    }
}

fun main() {
    val l = TimeUnit.transform(1.0, TimeUnit.TransformValues.HOURS, TimeUnit.TransformValues.SECONDS)
    println(l)
}