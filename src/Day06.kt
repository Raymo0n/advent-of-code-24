fun main() {
    fun getNextPosition(position: Pair<Int, Int>, direction: Int): Pair<Int, Int> {
        return when (direction) {
            0 -> {
                position.first - 1 to position.second
            }

            1 -> {
                position.first to position.second + 1
            }

            2 -> {
                position.first + 1 to position.second
            }

            3 -> {
                position.first to position.second - 1
            }

            else -> error("wrong direction")
        }
    }

    fun isOOB(position: Pair<Int, Int>, grid: Array<CharArray>): Boolean {
        return position.first < 0 || position.first >= grid.size || position.second < 0 || position.second >= grid[0].size
    }

    fun isObstacle(position: Pair<Int, Int>, grid: Array<CharArray>): Boolean {
        return grid[position.first][position.second] == '#'
    }

    fun recursiveWalk(position: Pair<Int, Int>, direction: Int, grid: Array<CharArray>) {
        grid[position.first][position.second] = 'X'
        val nextPosition = getNextPosition(position, direction)
        if (isOOB(nextPosition, grid)) {
            return
        }
        if (isObstacle(nextPosition, grid)) {
            recursiveWalk(position, (direction + 1) % 4, grid)
            return
        }
        recursiveWalk(nextPosition, direction, grid)
    }


    fun part1(input: List<String>): Int {
        val charArray = input
            .map { it.toCharArray() }
            .toTypedArray()

        val guard =
            charArray.mapIndexed { row, chars ->
                chars.mapIndexed { column, c -> if (c == '^') Pair(row, column) else null }
                    .filterNotNull()
                    .firstOrNull()
            }.filterNotNull()
                .firstOrNull()

        checkNotNull(guard)

        recursiveWalk(guard, 0, charArray,)

        return charArray.sumOf {
            it.count { it == 'X' }
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val exampleInput = readInput("Day06_example")
    checkEquals(part1(exampleInput), 41)
    checkEquals(part2(exampleInput), 0)

    val input = readInput("Day06")
    "Solution part 1: ${part1(input)}".println()
    "Solution part 2: ${part2(input)}".println()
}