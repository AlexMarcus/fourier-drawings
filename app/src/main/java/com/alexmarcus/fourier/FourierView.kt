package com.alexmarcus.fourier

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ComposePathEffect
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class FourierView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var ellipsoids: Set<Ellipsoid> = setOf()
        set(value) {
            field = value
            postInvalidateOnAnimation()
        }

//    var pathPoints: List<Pair<Float, Float>> = listOf()
//        set(value) {
//            field = value
//            postInvalidateOnAnimation()
//        }

    private val ellipsoidPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        strokeWidth = 2f
        style = Paint.Style.STROKE
        alpha = 50
    }

    private val drawPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }

    val points = mutableListOf<PointF>()
    val oldPoints = mutableListOf<PointF>()
    val maxPoints = 600

    val scaleMatrix = Matrix()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // draw axes
        // x-axis
        canvas?.drawLine(0f, height/2f, width.toFloat(), height/2f, ellipsoidPaint)

        // y-axis
        canvas?.drawLine(width/2f, 0f, width/2f, height.toFloat(), ellipsoidPaint)

        ellipsoids.forEach {
            drawEllipsoid(it, canvas)
        }
    }

    fun updateDataSet(ellipsoidSet: Set<Ellipsoid>) {
        if(ellipsoidSet.size != ellipsoids.size) {
            oldPoints.clear()
            oldPoints.addAll(points)
            points.clear()
        }

        ellipsoids = ellipsoidSet
    }

    private fun drawEllipsoid(ellipsoid: Ellipsoid, canvas: Canvas?) {
        canvas?.let { c ->
            c.drawCircle(ellipsoid.x, ellipsoid.y, ellipsoid.radius, ellipsoidPaint)

            // calculate end point of line

            val (stopX, stopY) = ellipsoid.getArrowEnd()

            c.drawLine(ellipsoid.x, ellipsoid.y, stopX, stopY, ellipsoidPaint)

            if(ellipsoid.draw) {
                points.add(PointF(stopX, stopY))

                if(points.size + oldPoints.size > maxPoints) {
                    if(oldPoints.size > 0) oldPoints.removeAt(0) else points.removeAt(0)
                }

                for (i in 1 until oldPoints.size) {
                    val p1 = oldPoints[i-1]
                    val p2 = oldPoints[i]
                    c.drawLine(p1.x, p1.y, p2.x, p2.y, drawPaint)
                }


                for (i in 1 until points.size) {
                    val p1 = points[i-1]
                    val p2 = points[i]
                    c.drawLine(p1.x, p1.y, p2.x, p2.y, drawPaint)
                }
            }
        }
    }

    fun clear() {
        points.clear()
        oldPoints.clear()
    }
}