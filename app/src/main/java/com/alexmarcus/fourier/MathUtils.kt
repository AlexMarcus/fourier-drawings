package com.alexmarcus.fourier

import org.kotlinmath.Complex
import org.kotlinmath.I
import org.kotlinmath.complex
import org.kotlinmath.exp
import kotlin.math.E
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    println(complexToPolar(3.0f,4.0f))
    println(complexToPolar(-3.0f,-4.0f))
    println(complexToPolar(-300.0f,-463.0f))
    println(complexToPolar(-3.0f,4.0f))

    val complex = complex(-3.4, 5.6)
    val complex2 = complex * exp((-3 * twoPI * 0.98).I)
    println(complex2)
    println(complex2.toPolar())
}

// Computes the arctan of y/x from 0 - 2PI
fun atan3(y: Float, x: Float): Float {
    val theta = atan2(y,x)
    return if(theta > 0) theta else twoPI + theta
}

fun radius(y: Float, x: Float): Float = sqrt(y.pow(2) + x.pow(2))

fun complexToPolar(y: Float, x: Float): Pair<Float, Float> = Pair(radius(y, x), atan3(y, x))

fun Complex.toPolar() : Pair<Float, Float> = Pair(radius(im.toFloat(), re.toFloat()), atan3(im.toFloat(), re.toFloat()))

fun Pair<Float, Float>.toComplex() = complex(first, second)

fun fourierValue(point: Complex, t: Float, deltaT: Float, n: Int): Complex =
    point * exp((-n * 2 * PI * t).I) * deltaT