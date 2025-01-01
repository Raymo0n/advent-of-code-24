private enum class Direction(
    val function: (Location) -> Location,
    val groupBy: (Location) -> Int,
    val cont: (Location) -> Int
) {
    North(Location::north, { it.first }, { it.second }),
    East(Location::east, { it.second }, { it.first }),
    South(Location::south, { it.first }, { it.second }),
    West(Location::west, { it.second }, { it.first }),
}

fun main() {

    val comparator: Comparator<Location> = Comparator { l1, l2 -> l1.first - l2.first + l1.second - l2.second }

    fun Grid<Char>.getRegion(location: Location): Char {
        if (this.isOutOfBounds(location)) {
            return '-'
        }
        return this[location]
    }

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        return this.first + other.first to this.second + other.second
    }

    fun Grid<Char>.getRegionSides(
        location: Location,
        perimeter: Grid<Boolean>,
    ): Pair<Int, List<Pair<Location, Direction>>> {
        if (perimeter[location]) {
            return 0 to emptyList()
        }
        val currentRegion = this.getRegion(location)
        perimeter[location] = true
        val sameRegion = Direction.entries.map { it.function(location) }
            .filter { getRegion(it) == currentRegion }
            .map { getRegionSides(it, perimeter) }
        val edges = Direction.entries.filter { getRegion(it.function(location)) != currentRegion }
            .map { location to it }
        return sameRegion
            .fold(
                initial = 1 to edges,
                operation = { acc, pair -> acc.first + pair.first to listOf(acc.second, pair.second).flatten() })
    }

    fun getRegions(grid: Grid<Char>, perimeter: Grid<Boolean>): List<Pair<Char, Int>> {
        return grid.mapIndexed { row, chars ->
            chars.mapIndexed { column, c ->
                val location = row to column
                if (perimeter[location]) {
                    null
                } else {
                    val regionAmount = grid.getRegionSides(location, perimeter)
                    c to regionAmount.first * regionAmount.second.size
                }
            }.filterNotNull()
        }.flatten()
    }

    fun countSides(regionAmount: Pair<Int, List<Pair<Location, Direction>>>): Int {
        return regionAmount.second.groupBy(keySelector = { it.second }, valueTransform = { it.first })
            .map { entry ->
                entry.value.groupBy(keySelector = { entry.key.groupBy(it) }, valueTransform = { it })
                    .values
                    .sumOf {
                        val sortedWith = it.sortedWith(comparator)
                        var last: Int? = null
                        var amountSides = 0
                        for (pair in sortedWith) {
                            val cont = entry.key.cont(pair)
                            if (last == null) {
                                last = cont
                                amountSides++
                            } else {
                                if (last + 1 != cont) {
                                    amountSides++
                                }
                                last = cont
                            }
                        }
                        amountSides
                    }
            }.sum()
    }

    fun getRegionsPart2(grid: Grid<Char>, perimeter: Grid<Boolean>): List<Pair<Char, Int>> {
        return grid.mapIndexed { row, chars ->
            chars.mapIndexed { column, c ->
                val location = row to column
                if (perimeter[location]) {
                    null
                } else {
                    val regionAmount = grid.getRegionSides(location, perimeter)
                    val sides = countSides(regionAmount)
                    c to regionAmount.first * sides
                }
            }.filterNotNull()
        }.flatten()
    }


    fun part1(input: List<String>): Long {
        val grid = input.map { it.toCharArray().toTypedArray() }.toTypedArray()
        val perimeter = Array(size = grid.size, init = { Array(size = grid[0].size, init = { false }) })
        return getRegions(grid, perimeter).sumOf { it.second }.toLong()
    }

    fun part2(input: List<String>): Long {
        val grid = input.map { it.toCharArray().toTypedArray() }.toTypedArray()
        val perimeter = Array(size = grid.size, init = { Array(size = grid[0].size, init = { false }) })
        return getRegionsPart2(grid, perimeter).sumOf { it.second }.toLong()
    }

    val exampleInput = readInput("Day12_example")
    checkEquals(part1(exampleInput), 1930L)
    checkEquals(part2(exampleInput), 1206L)

    val input = readInput("Day12")
    "Solution part 1: ${part1(input)}".println()
    "Solution part 2: ${part2(input)}".println()
}