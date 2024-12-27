fun main() {

    fun isPossible(result: Long, operands: List<Long>): Boolean {
        if (operands.size == 1) {
            if (result == operands[0]) {
                return true
            }
            return false
        }
        if (result < operands[0]) {
            return false
        }
        if (result % operands[0] == 0L) {
            if (isPossible(result / operands[0], operands.subList(fromIndex = 1, toIndex = operands.size))) {
                return true
            }
        }
        return isPossible(result - operands[0], operands.subList(fromIndex = 1, toIndex = operands.size))
    }

    fun isPossiblePart2(result: Long, intermediate: Long, operands: List<Long>): Boolean {
        if (intermediate > result) {
            return false
        }

        if (operands.size == 0) {
            if (result == intermediate) {
                return true
            }
            return false
        }

        return isPossiblePart2(
            result,
            intermediate * operands[0],
            operands.subList(fromIndex = 1, toIndex = operands.size)
        )
                || isPossiblePart2(
            result,
            intermediate + operands[0],
            operands.subList(fromIndex = 1, toIndex = operands.size)
        )
                || isPossiblePart2(
            result,
            (intermediate.toString() + operands[0].toString()).toLong(),
            operands.subList(fromIndex = 1, toIndex = operands.size)
        )
    }

    fun part1(input: List<String>): Long {
        val parsed = input.map {
            it.split(':').let { it[0].toLong() to it[1].trim(' ').split(' ').let { it.map { it.toLong() }.reversed() } }
        }

        return parsed.filter { isPossible(it.first, it.second) }
            .sumOf { it.first }
    }

    fun part2(input: List<String>): Long {
        val parsed = input.map {
            it.split(':').let { it[0].toLong() to it[1].trim(' ').split(' ').let { it.map { it.toLong() } } }
        }

        return parsed.filter { isPossiblePart2(it.first, it.second[0], it.second.subList(1, it.second.size)) }
            .sumOf { it.first }
    }

    val exampleInput = readInput("Day07_example")
    checkEquals(part1(exampleInput), 3749L)
    checkEquals(part2(exampleInput), 11387L)

    val input = readInput("Day07")
    "Solution part 1: ${part1(input)}".println()
    "Solution part 2: ${part2(input)}".println()
}