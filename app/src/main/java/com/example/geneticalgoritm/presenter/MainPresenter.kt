package com.example.geneticalgoritm.presenter

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.geneticalgoritm.R
import com.example.geneticalgoritm.model.MainModel
import com.example.geneticalgoritm.view.MainActivity
import kotlinx.coroutines.launch

class MainPresenter(activity: MainActivity) {
    var model = MainModel()
    private var view = activity


    public fun solve() {
        model.reload()
        while (!model.alg.isSolve()) {

            model.alg.population = ArrayList(model.alg.selection())

            view.drawGraph(
                model.chromosomeLen,
                model.alg.getWay(model.alg.bestGenotype(1).first())
            )
//            view.findViewById<TextView>(R.id.text).text =
//                view.findViewById<TextView>(R.id.text).text.toString() + "S"

        }
            view.drawGraph(model.chromosomeLen, model.alg.getWay(model.alg.bestGenotype(1).first()))
//            view.findViewById<TextView>(R.id.text).text = "solved"

    }


//    public fun solve() {
//        model.reload()
//        view.drawGraph(model)
////        view.findViewById<TextView>(R.id.text).text = "now started"
////        view.lifecycleScope.launch { solve(2, 2) }
//
//    }
//
//    public suspend fun solve(count: Int) {
//        if (!model.alg.isSolve()) {
//            model.alg.population = ArrayList(model.alg.selection())
//            view.apply {
//                lifecycleScope.launch {
//                    view.drawGraph(
//                        model.chromosomeLen,
//                        model.alg.getWay(model.alg.bestGenotype(1).first())
//                    )
//                    view.findViewById<TextView>(R.id.text).text =
//                        view.findViewById<TextView>(R.id.text).text.toString() + "S"
//                }.join()
//            }
//            solve(1)
//        } else {
//            view.drawGraph(model.chromosomeLen, model.alg.getWay(model.alg.bestGenotype(1).first()))
////            view.findViewById<TextView>(R.id.text).text = "solved"
//        }
//    }
//
//    public suspend fun solve(count: Int, c: Int) {
//        while (!model.alg.isSolve()) {
//            model.alg.population = ArrayList(model.alg.selection())
//            view.drawGraph(
//                model.chromosomeLen,
//                model.alg.getWay(model.alg.bestGenotype(1).first())
//            )
////            view.findViewById<TextView>(R.id.text).text =
////                view.findViewById<TextView>(R.id.text).text.toString() + "S"
//
//        }
////        view.drawGraph(model.chromosomeLen, model.alg.getWay(model.alg.bestGenotype(1).first()))
////            view.findViewById<TextView>(R.id.text).text = "solved"
//    }


    public fun getNewParameters(
        newPopulationCount: Int,
        newChromosomeLen: Int,
        newGenerationCount: Int,
        newCrossoverChance: Double,
        newMutationChance: Double
    ) {
        model.apply {
            populationCount = newPopulationCount
            chromosomeLen = newChromosomeLen
            generationCount = newGenerationCount
            crossoverChance = newCrossoverChance
            mutationChance = newMutationChance

        }
    }
}