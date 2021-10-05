package com.example.geneticalgoritm.presenter

import com.example.geneticalgoritm.model.MainModel
import com.example.geneticalgoritm.view.MainActivity

class MainPresenter(activity: MainActivity) {
    private var model = MainModel()
    private var view = activity


    public fun solve(count: Int = 1) {
        while (!model.alg.isSolve()) {
            model.alg.population = ArrayList(model.alg.selection())
            view.drawGraph(model.chromosomeLen, model.alg.getWay(model.alg.bestGenotype(1).first()))
        }
    }
}