private typealias Location = Pair<Int, Int>
private typealias Grid<T> = Array<Array<T>>

fun main() {

    fun Location.north(): Location {
        return Location(this.first - 1, this.second)
    }

    fun Location.east(): Location {
        return Location(this.first, this.second + 1)
    }

    fun Location.south(): Location {
        return Location(this.first + 1, this.second)
    }

    fun Location.west(): Location {
        return Location(this.first, this.second - 1)
    }

    operator fun <T> Grid<T>.get(value: Location): T {
        return this[value.first][value.second]
    }

    operator fun <T> Grid<T>.set(index: Location, value: T) {
        this[index.first][index.second] = value
    }

    fun <T> Grid<T>.isOutOfBounds(value: Location): Boolean {
        return value.first < 0 || value.second < 0 || value.first >= this.size || if (this.isEmpty()) false else value.second >= this[0].size
    }

    fun getDistinctTrailheads(location: Location, topography: Grid<Int>, paths: Grid<Set<Location>?>): Set<Location> {
        if (paths[location] != null) {
            return paths[location]!!
        }
        val height = topography[location]
        paths[location] = sequenceOf(location.north(), location.east(), location.south(), location.west())
            .filter { !topography.isOutOfBounds(it) && topography[it] == height + 1 }
            .map { getDistinctTrailheads(it, topography, paths) }
            .flatten()
            .toSet()
        return paths[location]!!
    }

    fun getDistinctTrails(location: Location, topography: Grid<Int>, paths: Grid<Int?>): Int {
        if (paths[location] != null) {
            return paths[location]!!
        }
        val height = topography[location]
        paths[location] = sequenceOf(location.north(), location.east(), location.south(), location.west())
            .filter { !topography.isOutOfBounds(it) && topography[it] == height + 1 }
            .sumOf { getDistinctTrails(it, topography, paths) }
        return paths[location]!!
    }

    fun part2(input: List<String>): Long {
        val topography = input.map { it.map { height -> height.digitToInt() }.toTypedArray() }
            .toTypedArray()
        val paths = Array(
            size = topography.size,
            init = { Array<Int?>(size = topography[0].size, init = { null }) })
        topography.forEachIndexed { row, ints ->
            ints.forEachIndexed { column, i ->
                if (i == 9) paths[row][column] = 1
            }
        }
        return topography.mapIndexed { row, ints ->
            ints.mapIndexed { column, i ->
                if (i == 0) {
                    getDistinctTrails(location = Location(row, column), topography, paths)
                } else {
                    0
                }
            }
        }.flatten()
            .sum()
            .toLong()
    }

    fun part1(input: List<String>): Long {
        val topography = input.map { it.map { height -> height.digitToInt() }.toTypedArray() }
            .toTypedArray()
        val paths = Array(
            size = topography.size,
            init = { Array<Set<Location>?>(size = topography[0].size, init = { null }) })
        topography.forEachIndexed { row, ints ->
            ints.forEachIndexed { column, i ->
                if (i == 9) paths[row][column] = mutableSetOf(row to column)
            }
        }
        return topography.mapIndexed { row, ints ->
            ints.mapIndexed { column, i ->
                if (i == 0) {
                    getDistinctTrailheads(location = Location(row, column), topography, paths).size
                } else {
                    0
                }
            }
        }.flatten()
            .sum()
            .toLong()
    }

    val exampleInput = readInput("Day10_example")
    checkEquals(part1(exampleInput), 36L)
    checkEquals(part2(exampleInput), 81L)

    val input = readInput("Day10")
    "Solution part 1: ${part1(input)}".println()
    "Solution part 2: ${part2(input)}".println()
}