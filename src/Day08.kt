fun main() {

    operator fun Pair<Int, Int>.minus(pair: Pair<Int, Int>): Pair<Int, Int> {
        return this.first - pair.first to this.second - pair.second
    }

    operator fun Pair<Int, Int>.times(times: Int): Pair<Int, Int> {
        return this.first * times to this.second * times
    }

    operator fun Int.times(times: Pair<Int, Int>): Pair<Int, Int> {
        return times * this
    }

    fun calcAntiNodes(
        location1: Pair<Int, Int>,
        location2: Pair<Int, Int>,
        collection: MutableCollection<Pair<Int, Int>>
    ) {
        collection.add(2 * location1 - location2)
        collection.add(2 * location2 - location1)
    }

    fun isOOB(location: Pair<Int, Int>, rowLimit: Int, columnLimit: Int): Boolean {
        return location.first < 0 || location.second < 0 || location.first > rowLimit || location.second > columnLimit
    }


    fun calcAntiNodesWithResonant(
        location1: Pair<Int, Int>,
        location2: Pair<Int, Int>,
        collection: MutableCollection<Pair<Int, Int>>,
        rowLimit: Int,
        columnLimit: Int
    ) {
        val distance1 = location1 - location2
        var antiNode = location1 - distance1
        while (!isOOB(antiNode, rowLimit, columnLimit)) {
            collection.add(antiNode)
            antiNode -= distance1
        }
        val distance2 = location2 - location1
        antiNode = location2 - distance2
        while (!isOOB(antiNode, rowLimit, columnLimit)) {
            collection.add(antiNode)
            antiNode -= distance2
        }
    }


    fun getGroupByFrequency(input: List<String>): Map<Char, List<Pair<Int, Int>>> {
        return input.mapIndexed { row, s ->
            s.mapIndexed { column, c -> if (c != '.') c to (row to column) else null }
                .filterNotNull()
        }
            .flatten()
            .groupBy(keySelector = { it.first }, valueTransform = { it.second })
    }

    fun part1(input: List<String>): Int {
        val groupByFrequency = getGroupByFrequency(input)

        val rowLimit = input.size - 1
        val columnLimit = input[0].length - 1

        return groupByFrequency.values.asSequence().map {
            val mutableListOf = mutableListOf<Pair<Int, Int>>()
            it.forEachIndexed { index, pair ->
                it.drop(index + 1).forEach { calcAntiNodes(pair, it, mutableListOf) }
            }
            mutableListOf
        }.flatten()
            .filter { !isOOB(it, rowLimit, columnLimit) }
            .toSet()
            .count()
    }

    fun part2(input: List<String>): Int {
        val groupByFrequency = getGroupByFrequency(input)

        val rowLimit = input.size - 1
        val columnLimit = input[0].length - 1

        return groupByFrequency.values.asSequence().map {
            val mutableListOf = mutableListOf<Pair<Int, Int>>()
            it.forEachIndexed { index, pair ->
                it.drop(index + 1).forEach { calcAntiNodesWithResonant(pair, it, mutableListOf, rowLimit, columnLimit) }
            }
            mutableListOf
        }.flatten()
            .filter { !isOOB(it, rowLimit, columnLimit) }
            .toSet()
            .count()
    }

    val exampleInput = readInput("Day08_example")
    checkEquals(part1(exampleInput), 14)
    checkEquals(part2(exampleInput), 34)

    val input = readInput("Day08")
    "Solution part 1: ${part1(input)}".println()
    "Solution part 2: ${part2(input)}".println()
}