fun main() {
    fun getNextPosition(position: Location, direction: Int): Location {
        return when (direction) {
            1 -> {
                position.north()
            }

            2 -> {
                position.east()
            }

            4 -> {
                position.south()
            }

            8 -> {
                position.west()
            }

            else -> error("wrong direction")
        }
    }

    fun getNextDirection(direction: Int): Int {
        return when (direction) {
            1, 2, 4 -> direction.shl(1)
            8 -> 1
            else -> error("wrong directions")
        }
    }

    fun isObstacle(position: Pair<Int, Int>, grid: Grid<Char>): Boolean {
        return grid[position.first][position.second] == '#'
    }

    fun isLoop(position: Pair<Int, Int>, direction: Int, visited: Grid<Int>): Boolean {
        return visited[position.first][position.second].and(direction) == 1
    }

    fun simulateGuard(
        startPosition: Pair<Int, Int>,
        grid: Grid<Char>,
        visited: Grid<Int>
    ): Boolean {
        var guardPosition = startPosition
        var myDirection = 1
        while (true) {
            if (isLoop(guardPosition, myDirection, visited)) {
                return true
            }
            visited[guardPosition.first][guardPosition.second] += myDirection
            val nextPosition = getNextPosition(guardPosition, myDirection)
            if (grid.isOutOfBounds(nextPosition)) {
                return false
            }
            if (isObstacle(nextPosition, grid)) {
                myDirection = getNextDirection(myDirection)
            } else {
                guardPosition = nextPosition
            }

        }
    }


    fun part1(input: List<String>): Int {
        val obstacleGrid = input
            .map { it.toCharArray().toTypedArray() }
            .toTypedArray()
        val visited = Array(obstacleGrid.size, init = { Array(obstacleGrid[0].size, init = { 0 }) })

        val guard =
            obstacleGrid.mapIndexed { row, chars ->
                chars.mapIndexed { column, c -> if (c == '^') Pair(row, column) else null }
                    .filterNotNull()
                    .firstOrNull()
            }.filterNotNull()
                .firstOrNull()

        checkNotNull(guard)

        simulateGuard(guard, obstacleGrid, visited)

        return visited.sumOf {
            it.count { it > 0 }
        }
    }

    fun part2(input: List<String>): Int {
        val obstacleGrid = input
            .map { it.toCharArray().toTypedArray() }
            .toTypedArray()
        val visitedFirst = Array(obstacleGrid.size, init = { Array(obstacleGrid[0].size, init = { 0 }) })

        val guard =
            obstacleGrid.mapIndexed { row, chars ->
                chars.mapIndexed { column, c -> if (c == '^') Pair(row, column) else null }
                    .filterNotNull()
                    .firstOrNull()
            }.filterNotNull()
                .firstOrNull()

        checkNotNull(guard)

        simulateGuard(guard, obstacleGrid, visitedFirst)

        val obstaclePositions = visitedFirst.flatMapIndexed { row, ints ->
            ints.mapIndexed { column, i -> if (i > 0) row to column else null }
                .filterNotNull()
        }

        return obstaclePositions.count {
            obstacleGrid[it.first][it.second] = '#'
            val visited = Array(obstacleGrid.size, init = { Array(obstacleGrid[0].size, init = { 0 }) })
            val recursiveWalk = simulateGuard(guard, obstacleGrid, visited)
            obstacleGrid[it.first][it.second] = if (recursiveWalk) '-' else '!'
            recursiveWalk
        }
    }

    val exampleInput = readInput("Day06_example")
    checkEquals(part1(exampleInput), 41)
    checkEquals(part2(exampleInput), 6)

    val input = readInput("Day06")
    "Solution part 1: ${part1(input)}".println()
    "Solution part 2: ${part2(input)}".println()
}