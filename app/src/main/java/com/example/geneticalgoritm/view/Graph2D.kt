package com.example.geneticalgoritm.view

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import android.view.SurfaceHolder

import android.view.SurfaceView
import com.example.geneticalgoritm.model.MainModel


class Graph2D(
    context: Context?,
    var nodes: Int = 20,
    var edges: ArrayList<Triple<Int, Int, Double>> = ArrayList()
) : View(context) {

    var gen = 0
    var solving = false
    var data = MainModel()
        set(value) {
            field = value
            field.reload()
        }

    override fun onDraw(canvas: Canvas?) {
        var painter = Paint()
        painter.apply {
            style = Paint.Style.FILL // стиль Заливка
            color = Color.WHITE // закрашиваем холст белым цветом
        }
        canvas?.drawPaint(painter)

        painter.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = Color.GRAY
        }

        val R = min(width, height) * 0.4
        val center = PointF((width / 2.0).toFloat(), (height / 2.0).toFloat())

        val tmp = ArrayList<Pair<Int, Int>>()
        for (i in 0..nodes - 2)
            for (j in i + 1..nodes - 1)
                tmp.add(Pair(i, j))

        for (pair in tmp) {
            canvas?.drawLine(
                (center.x + R * cos(pair.first * 2 * PI / nodes)).toFloat(),
                (center.y - R * sin(pair.first * 2 * PI / nodes)).toFloat(),
                (center.x + R * cos(pair.second * 2 * PI / nodes)).toFloat(),
                (center.y - R * sin(pair.second * 2 * PI / nodes)).toFloat(),
                painter
            )
        }

        painter.color = Color.RED
        painter.strokeWidth = 3.0F
        for (pair in edges) {
            canvas?.drawLine(
                (center.x + R * cos(pair.first * 2 * PI / nodes)).toFloat(),
                (center.y - R * sin(pair.first * 2 * PI / nodes)).toFloat(),
                (center.x + R * cos(pair.second * 2 * PI / nodes)).toFloat(),
                (center.y - R * sin(pair.second * 2 * PI / nodes)).toFloat(),
                painter
            )
        }

        painter.color = Color.BLACK

        var i = 0
        while (i < nodes) {
            painter.color = Color.BLACK

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
                (center.x + R * cos(i * 2 * PI / nodes) - painter.measureText("${i + 1}") / 2).toFloat(),
                (center.y - R * sin(i * 2 * PI / nodes) + tmp.height() / 2).toFloat(),
                painter
            )

            painter.color = Color.BLACK
            i++

        }
//        canvas?.drawText(
//            "${gen++}",
//            50F,
//            50F,
//            painter
//        )
//        for (item in 1..1000){
//            var fn = item*item*item*item
//            Thread.sleep(0, 1)
//        }
    }
}

//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//
//        drawBasic(canvas)
//
//        if(solving) {
//
//            while (!data.alg.isSolve()) {
//                data.alg.population = ArrayList(data.alg.selection())
//
//
//                nodes = data.chromosomeLen
//                edges = data.alg.getWay(data.alg.bestGenotype(1).first())
////                view.drawGraph(
////                    data.chromosomeLen,
////                    data.alg.getWay(data.alg.bestGenotype(1).first())
////                )
//                drawBasic(canvas)
//            }
//            solving = false
//        }
//
//
//
//    }
//}


//internal class Graph2D(
//    context: Context?,
//    private var nodes: Int = 10,
//    private var edges: ArrayList<Triple<Int, Int, Double>> = ArrayList()
//) : SurfaceView(context),
//    SurfaceHolder.Callback {
//    private var drawThread: DrawThread? = null
//    override fun surfaceChanged(
//        holder: SurfaceHolder, format: Int, width: Int,
//        height: Int
//    ) {
//    }
//
//    override fun surfaceCreated(holder: SurfaceHolder) {
//        drawThread = DrawThread(getHolder())
//        drawThread!!.setRunning(true)
//        drawThread!!.start()
//    }
//
//    override fun surfaceDestroyed(holder: SurfaceHolder) {
//        var retry = true
//        drawThread!!.setRunning(false)
//        while (retry) {
//            try {
//                drawThread!!.join()
//                retry = false
//            } catch (e: InterruptedException) {
//            }
//        }
//    }
//
//    internal inner class DrawThread(private val surfaceHolder: SurfaceHolder) : Thread() {
//        private var running = false
//        fun setRunning(running: Boolean) {
//            this.running = running
//        }
//
//        override fun run() {
//            var canvas: Canvas?
//            while (running) {
//                canvas = null
//                try {
//                    canvas = surfaceHolder.lockCanvas(null)
//                    if (canvas == null) continue
//                    draw(canvas, nodes, edges, width, height)
//
//                } finally {
//                    if (canvas == null) {
//                        surfaceHolder.unlockCanvasAndPost(canvas)
//                    }
//                }
//            }
//        }
//    }
//
//    init {
//        holder.addCallback(this)
//    }
//}






















