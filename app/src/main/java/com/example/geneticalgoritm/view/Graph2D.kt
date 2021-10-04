package com.example.geneticalgoritm.view

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.View
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class Graph2D(
    context: Context?,
    private var nodes: Int = 10,
    private var edges: ArrayList<Triple<Int, Int, Double>> = ArrayList()
) : View(context) {
    private val painter = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        painter.apply {
            style = Paint.Style.FILL // стиль Заливка
            color = Color.WHITE // закрашиваем холст белым цветом
        }
        canvas?.drawPaint(painter)

        painter.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = Color.BLACK
        }
//        canvas?.drawCircle(
//            (width / 2).toFloat(),
//            (height / 2).toFloat(),
//            (min(width, height) / 4).toFloat(),
//            paint
//        )

        val R = min(width, height) * 0.4
        val center = PointF((width / 2.0).toFloat(), (height / 2.0).toFloat())

        for (pair in edges){
            canvas?.drawLine(
                (center.x + R * cos(pair.first * 2 * PI / nodes)).toFloat(),
                (center.y - R * sin(pair.first * 2 * PI / nodes)).toFloat(),
                (center.x + R * cos(pair.second * 2 * PI / nodes)).toFloat(),
                (center.y - R * sin(pair.second * 2 * PI / nodes)).toFloat(),
                painter
            )
        }

//        for(i in 0 until nodes-1){
//            for (j in i+1 until nodes) {
//                canvas?.drawLine(
//                    (center.x + R * cos(i * 2 * PI / nodes)).toFloat(),
//                    (center.y - R * sin(i * 2 * PI / nodes)).toFloat(),
//                    (center.x + R * cos(j * 2 * PI / nodes)).toFloat(),
//                    (center.y - R * sin(j * 2 * PI / nodes)).toFloat(),
//                    painter
//                )
//            }
//        }

        var i = 0
        while (i < nodes) {
            painter.color = Color.BLACK

            Log.d("bib", "$i bub")
            canvas?.drawCircle(
                (center.x + R * cos(i * 2 * PI / nodes)).toFloat(),
                (center.y - R * sin(i * 2 * PI / nodes)).toFloat(),
                (min(width, height) / nodes / 2).toFloat(), painter
            )
            val tmp = Rect()
            painter.getTextBounds("${i + 1}", 0, "${i + 1}".length, tmp)
            painter.textSize = (min(width, height) / nodes / 4).toFloat()
            painter.color = Color.WHITE

            canvas?.drawText(
                "${i + 1}",
                (center.x + R * cos(i * 2 * PI / nodes) - painter.measureText("${i + 1}")/2).toFloat(),
                (center.y - R * sin(i * 2 * PI / nodes) + tmp.height()/2).toFloat(),
                painter
            )
            i++
        }


    }
}