package com.example.geneticalgoritm.algoritm

import java.util.ArrayList
import kotlin.random.Random

class GenAlg(
    private var populationCount: Int =,
    private var chromosomeLen: Int = 20,
    private var crossoverChance: Double,
    private var mutationChance: Double
) {
    private lateinit var population: ArrayList<Genotype>
    private lateinit var graph: ArrayList<Triple<Int, Int, Double>>

    init {
        for (i in 0 until populationCount)
            population.add(Genotype(chromosomeLen))
        for (i in 0..chromosomeLen - 2) // количество хромосом = количеству вершин графа
            for (j in i + 1..chromosomeLen - 1)
                graph.add(Triple(i, j, Random.nextInt(1, 100).toDouble()))
    }

    public fun setPopulationCount(newPopCount: Int) {
        populationCount = newPopCount
        reload()
    }

    public fun setChromosomeLen(newChromLen: Int) {
        chromosomeLen = newChromLen
        reload()
        graph.clear()
        for (i in 0..chromosomeLen - 2)
            for (j in i + 1..chromosomeLen - 1)
                graph.add(Triple(i, j, Random.nextInt(1, 100).toDouble()))
    }

    private fun reload() {
        population.clear()
        for (i in 0 until populationCount)
            population.add(Genotype(chromosomeLen))
    }

    public fun fitness(index: Int): Double {
        var sum = 0.0
        var tmp = population[index].getChromosome()
        for (i in 1 until populationCount) {
            sum += graph.find { triple -> triple.first == tmp[i - 1] && triple.second == tmp[i] }!!.third
        }
        return sum
    }

    public fun selection(count: Int = populationCount / 2) {
        var bestPop = population.mapIndexed() { index, _ -> Pair(index, fitness(index)) }
            .sortedWith(compareBy { it.second }).subList(0, count).drop(count)
            .map { pair -> population[pair.first] } // получаем половину наиболее приспособленных особей
        // в поредке возрастания значения функции приспособленности
        var newPopulation = ArrayList<Genotype>()
        // TODO дописать тут
        if (bestPop.size % 2 == 1) {
            if (Random.nextInt(0, 1) == 1)

            newPopulation.add(bestPop.last())
        }


    }

    private fun mutateAll(gen: ArrayList<Genotype>): ArrayList<Genotype> {
        var newGen = ArrayList<Genotype>()
        gen.map { genotype -> newGen.add(Genotype(genotype.mutation(mutationChance))) }
        return newGen
    }


    //На вход всегда передается четное количество особей
    private fun crossoverAll(gen: ArrayList<Genotype>): ArrayList<Genotype> {
        val range = 0 until gen.size
        // временный массив для создания индексов по которым будет проходить обмен генами
        var swapArr = ArrayList<Int>()
        for (i in range) {
            var tmp = range.random()
            while (swapArr.contains(tmp)) {
                tmp = range.random()
            }
            swapArr.add(tmp)
        }

        var newGen = ArrayList<Genotype>()
        for (i in 0 until swapArr.size step 2) {
            var tmp = gen[i].crossover(gen[i + 1], crossoverChance)
            newGen.addAll(arrayListOf(Genotype(tmp.first), Genotype(tmp.second)))
        }
        return newGen

    }

    class Genotype() {
        private var len: Int = 0
        private var chromosome = ArrayList<Int>()

        private var range = 0..0


        constructor(n: Int) : this() {
            len = n
            range = 1..len
            for (i in range) {
                var tmp = range.random()
                while (chromosome.contains(tmp)) {
                    tmp = range.random()
                }
                chromosome.add(tmp)
            }
        }

        constructor(n: Int, chrom: ArrayList<Int>) : this() {
            len = n
            chromosome = chrom
        }

        constructor(chrom: ArrayList<Int>) : this(chrom.size, chrom) {}

        public fun getLen() = len
        public fun setLen(newLen: Int) {
            len = newLen
        }

        public fun getChromosome() = chromosome

        public fun mutation(chance: Double): ArrayList<Int> {
            var newChromosome = chromosome
            if (Random.nextDouble(0.0, 1.0) < chance) {
                var first = range.random() - 1
                var second = first
                do {
                    second = range.random() - 1
                } while (first == second)
                val tmp = newChromosome[first]
                newChromosome[first] = newChromosome[second]
                newChromosome[second] = newChromosome[first]
            }
            return newChromosome
        }

        public fun crossover(
            another: Genotype,
            chance: Double
        ): Pair<ArrayList<Int>, ArrayList<Int>> {
            var left = chromosome
            var right = another.getChromosome()
            if (Random.nextDouble(0.0, 1.0) < chance) {
                var barrier = range.random() - 1
                for (i in 0 until len) {
                    if (i < -barrier) {
                        left[i] = chromosome[i]
                        right[i] = another.getChromosome()[i]
                    } else {
                        left[i] = another.getChromosome()[i]
                        right[i] = chromosome[i]
                    }
                }

            }
            return Pair(left, right)
        }

    }
}