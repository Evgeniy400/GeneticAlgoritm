import kotlin.math.roundToInt
import kotlin.random.Random

class GenAlg(
    newPopulationCount: Int = 40,
    newChromosomeLen: Int = 20,
    newCrossoverChance: Double = 0.7,
    newMutationChance: Double = 0.1,
    newMaxGenerationCount: Int = 100
) {
    var population = ArrayList<Genotype>()
    var populationCount: Int = newPopulationCount
        set(value) {
            field = if (value <= 0)
                1
            else
                value
            reload()
        }
    var chromosomeLen: Int = newChromosomeLen
        set(value) {
            field = if (value <= 1)
                2
            else
                value
            reload()
            graph.clear()
            for (i in 0..chromosomeLen - 2)
                for (j in i + 1..chromosomeLen - 1)
                    graph.add(Triple(i, j, Random.nextInt(1, 100).toDouble()))
        }
    var crossoverChance: Double = newCrossoverChance
        set(value) =
            if (value < 0.0)
                field = 0.0
            else {
                if (value > 1.0)
                    field = 1.0
                else
                    field = value
            }
    var mutationChance: Double = newMutationChance
        set(value) =
            if (value < 0.0)
                field = 0.0
            else {
                if (value > 1.0)
                    field = 1.0
                else
                    field = value
            }
    var maxGenerationCount: Int = newMaxGenerationCount
        set(value) {
            field = if (value <= 0)
                1
            else
                value
            generation = 0
        }


    private var graph = ArrayList<Triple<Int, Int, Double>>()
    private var generation = 0


    init {
        for (i in 0 until populationCount)
            population.add(Genotype(chromosomeLen))
        for (i in 1..chromosomeLen - 1) // количество хромосом = количеству вершин графа
            for (j in i + 1..chromosomeLen)
                if (i + 1 == j || i == 1 && j == chromosomeLen)
                    graph.add(Triple(i, j, 1.0))
                else
                    graph.add(Triple(i, j, Random.nextInt(2, 100).toDouble()))

    }


    private fun reload() {
        generation = 0
        population.clear()
        for (i in 0 until populationCount)
            population.add(Genotype(chromosomeLen))
    }

    public fun fitness(index: Int): Double {
        //в Алгоритме мы ищем максимум функции, но по задаче нужно
        //найти наименьший путь, поэтому преобразуем ф-цию суммы
        var sum = 0.0
        var chromosome = population[index].getChromosome()
        for (i in 1 until chromosomeLen) {
            sum += graph.find { triple -> (triple.first == chromosome[i - 1] && triple.second == chromosome[i]) || (triple.first == chromosome[i] && triple.second == chromosome[i - 1]) }!!.third
        }
        sum += graph.find { triple -> (triple.first == chromosome[0] && triple.second == chromosome.last()) || (triple.first == chromosome.last() && triple.second == chromosome[0]) }!!.third
        return 1.0 / sum
    }

    public fun bestGenotype(count: Int = 1) =
        population.sortedBy { fitness(population.indexOf(it)) }.takeLast(count)

    public fun selection(): ArrayList<Genotype> {
        //выбираем особей согласно их функции приспособления - чем она выше тем выше шанс
        //перейти в новую популяцию
        var fitness = population.mapIndexed() { index, _ -> Pair(index, fitness(index)) }

        var translate: (Double) -> Int = { it ->
            var i = 0
            var localSum = 0.0
            while (i < fitness.size) {
                localSum += fitness[i++].second
                if (it < localSum)
                    break
            }
            fitness[i - 1].first
        }

        var bestPop = List(populationCount) {
            population[translate(Random.nextDouble(0.0, fitness.sumOf { pair -> pair.second }))]
        } as ArrayList

        var newPopulation = ArrayList<Genotype>()
        var tmp = ArrayList<Genotype>()

        //применение генетических операторов
        if (populationCount % 2 == 1) {
            if (Random.nextInt(0, 2) == 1) {
                tmp.add(Genotype(bestPop[Random.nextInt(populationCount)].getChromosome()))
                tmp.addAll(crossoverAll(bestPop.take(populationCount - 1) as ArrayList<Genotype>))
                newPopulation.addAll(mutateAll(tmp))
            } else {
                tmp.addAll(mutateAll(bestPop))
                newPopulation.addAll(crossoverAll(tmp.take(populationCount - 1) as ArrayList<Genotype>))
                newPopulation.add(Genotype(tmp[Random.nextInt(populationCount)].getChromosome()))
            }
        } else {
            if (Random.nextInt(0, 2) == 1) {
                tmp.addAll(crossoverAll(bestPop))
                newPopulation.addAll(mutateAll(tmp))
            } else {
                tmp.addAll(mutateAll(bestPop))
                newPopulation.addAll(crossoverAll(tmp))
            }
        }
        generation++
        return newPopulation
    }

    private fun mutateAll(gen: ArrayList<Genotype>): ArrayList<Genotype> =
        gen.map { genotype -> Genotype(genotype.mutation(mutationChance)) } as ArrayList


    //На вход всегда передается четное количество особей
    private fun crossoverAll(gen: ArrayList<Genotype>): ArrayList<Genotype> {
        val range = 0 until gen.size
        // временный массив для создания индексов по которым будет проходить обмен генами
        var swapArr = ArrayList<Int>()
        swapArr.add(range.random())
        for (i in 1 until gen.size) {
            var tmp = range.random()
            while (swapArr.contains(tmp)) {
                tmp = range.random()
            }
            swapArr.add(tmp)
        }

        var newGen = ArrayList<Genotype>()
        var i = 0
        while (i < swapArr.size) {
            var tmp = gen[i].crossover(gen[i + 1], crossoverChance)
            newGen.addAll(arrayListOf(Genotype(tmp.first), Genotype(tmp.second)))
            i += 2
        }

        return newGen

    }

    public fun isSolve(): Boolean = generation == maxGenerationCount

    public fun solve(count: Int = 1): List<Genotype> {
        while (!isSolve()) {
            population = ArrayList(selection())
//            allCorrect = population.find { !it.isCor() } == null
        }
        return bestGenotype(count)
    }


    public fun printGraph() {
        graph.forEach {
            println("${it.first} ⥄ ${it.second}: ${it.third}")
        }
    }

    public fun getWay(genotype: Genotype): ArrayList<Triple<Int, Int, Double>> {
        var points = genotype.getChromosome()
        var ret = ArrayList<Triple<Int, Int, Double>>()
        points.forEachIndexed { index, item ->
            if (index != 0) {
                ret.add(
                    Triple(
                        points[index - 1],
                        item,
                        graph.find { triple -> ((triple.first == points[index - 1] && triple.second == item)
                            || (triple.first == item && triple.second == points[index-1])) }!!.third)
                )
            }
        }
        return ret


//        return ArrayList(points.size){
//            points.forEachIndexed { index, i ->  add(Triple(i, i, 1.0))}
//        }

//        var sum = 0.0
//        var chromosome = population[index].getChromosome()
//        for (i in 1 until chromosomeLen) {
//            sum += graph.find { triple -> (triple.first == chromosome[i - 1] && triple.second == chromosome[i]) || (triple.first == chromosome[i] && triple.second == chromosome[i - 1]) }!!.third
//        }
//        sum += graph.find { triple -> (triple.first == chromosome[0] && triple.second == chromosome.last()) || (triple.first == chromosome.last() && triple.second == chromosome[0]) }!!.third
//        return 1.0 / sum
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
            range = 1..len
        }

        constructor(chrom: ArrayList<Int>) : this(chrom.size, chrom) {}

        public fun getLen() = len
        public fun setLen(newLen: Int) {
            len = newLen
        }

        public fun getChromosome() = chromosome

        public fun mutation(chance: Double): ArrayList<Int> {
            var newChromosome = ArrayList(chromosome)
            if (Random.nextDouble(0.0, 1.0) < chance) {
                var first = range.random() - 1
                var second = first
                do {
                    second = range.random() - 1
                } while (first == second)
                val tmp = newChromosome[first]
                newChromosome[first] = newChromosome[second]
                newChromosome[second] = tmp
            }
            return newChromosome
        }

        public fun crossover(
            another: Genotype,
            chance: Double
        ): Pair<ArrayList<Int>, ArrayList<Int>> {
            var left = ArrayList(chromosome)
            var right = ArrayList(another.getChromosome())
            if (Random.nextDouble(0.0, 1.0) < chance) {
                var indexes = ArrayList<Int>()
                for (i in 1 until (len * (1 - chance)).roundToInt()) {
                    indexes.add(Random.nextInt(0, len))
                    var tmp = Random.nextInt(0, len)
                    while (indexes.contains(tmp)) {
                        tmp = Random.nextInt(0, len)
                    }
                    indexes.add(tmp)
                }

                var tmpL = left.filterIndexed { index, item ->
                    !indexes.contains(index)
                }
                var tmpR = right.filterIndexed { _, i ->
                    tmpL.contains(i)
                }

                for (i in 0 until len) {
                    if (!indexes.contains(i)) {
                        left[i] = tmpR.first()
                        tmpR = tmpR.drop(1)
                    }
                }
                for (i in 0 until len) {
                    if (!indexes.contains(chromosome.indexOf(right[i]))) {
                        right[i] = tmpL.first()
                        tmpL = tmpL.drop(1)
                    }
                }
            }
            return Pair(left, right)
        }
    }
}