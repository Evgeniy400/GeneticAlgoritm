package com.example.geneticalgoritm.model

import GenAlg

class MainModel {
    fun reload() {
        alg = GenAlg(
            populationCount,
            chromosomeLen,
            crossoverChance,
            mutationChance,
            generationCount
        )
    }

    var alg = GenAlg()



    var populationCount: Int = 40
        set(value) = if (value <= 0)
            field = 1
        else
            field = value

    var chromosomeLen: Int = 20
        set(value) = if (value <= 1)
            field = 2
        else
            field = value

    var generationCount: Int = 100
        set(value) = if (value <= 0)
            field = 1
        else
            field = value

    var crossoverChance: Double = 0.7
        set(value) =
            if (value < 0.0)
                field = 0.0
            else {
                if (value > 1.0)
                    field = 1.0
                else
                    field = value
            }

    var mutationChance: Double = 0.1
        set(value) =
            if (value < 0.0)
                field = 0.0
            else {
                if (value > 1.0)
                    field = 1.0
                else
                    field = value
            }
}

