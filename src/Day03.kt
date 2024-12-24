fun main() {

    fun part1(input: List<String>): Int {
        val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
        return input.flatMap { regex.findAll(it) }
            .sumOf { matchResult ->
                matchResult.groupValues.drop(1)
                    .map { it.toInt() }
                    .reduce { a, b -> a * b }
            }
    }

    fun part2(input: List<String>): Int {
        val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""")
        var skip = false
        return input.flatMap { regex.findAll(it) }
            .sumOf { matchResult ->
                if (matchResult.value.startsWith("don't(")) {
                    skip = true
                    0
                } else if (matchResult.value.startsWith("do(")) {
                    skip = false
                    0
                } else if (skip) {
                    0
                } else {
                    matchResult.groupValues.drop(1)
                        .map { it.toInt() }
                        .reduce { a, b -> a * b }
                }
            }
    }

    val exampleInput = readInput("Day03_example")
    checkEquals(part1(exampleInput), 161)
    val exampleInput2 = readInput("Day03_example2")
    checkEquals(part2(exampleInput2), 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}