package com.alexmarcus.fourier

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

const val twoPI = (2 * PI).toFloat()

data class Ellipsoid(
    var x: Float,
    var y: Float,
    var freq: Float,
    var theta: Float,
    var radius: Float,
    var prev: Ellipsoid? = null,
    var draw: Boolean = false
) {

    fun getArrowEnd(): Pair<Float, Float> {
        val stopX = this.x + this.radius * cos(this.theta)
        val stopY = this.y - this.radius * sin(this.theta)
        return Pair(stopX, stopY)
    }

    fun rotate(deltaT: Long) {
        val deltaTheta = this.freq * twoPI * (deltaT / 5000f)
        theta += deltaTheta

        if(theta > twoPI) theta -= twoPI
        if(theta < -twoPI) theta += twoPI
    }
}