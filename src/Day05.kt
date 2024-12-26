fun main() {

    fun isSafe(update: List<String>, constraints: Map<String, List<String>>): Boolean {
        val illegal = mutableSetOf<String>()
        return update.all {
            if (illegal.contains(it)) {
                false
            } else {
                constraints[it]?.let { illegal.addAll(it) }
                true
            }
        }
    }

    fun part1(input: List<String>): Int {
        val constraints = input
            .filter { it.contains('|') }
            .map { it.split('|').let { split -> split[0] to split[1] } }
        val updatesReveredOrder = input.filter { !it.contains('|') && it.isNotBlank() }
            .map { it.split(',').toList().reversed() }.toList()
        val afterConstraints = constraints.groupBy(keySelector = { it.first }, valueTransform = { it.second })

        return updatesReveredOrder.filter { isSafe(it, afterConstraints) }
            .sumOf { it[it.size / 2].toInt() }
    }

    fun compare(
        a: String,
        b: String,
        afterConstraints: Map<String, Collection<String>>,
        beforeConstrains: Map<String, Collection<String>>
    ): Int {
        return if (afterConstraints[a]?.contains(b) == true) 1
        else if (beforeConstrains[a]?.contains(b) == true) -1
        else 0
    }

    fun part2(input: List<String>): Int {
        val constraints = input
            .filter { it.contains('|') }
            .map { it.split('|').let { split -> split[0] to split[1] } }
        val updatesReveredOrder = input.filter { !it.contains('|') && it.isNotBlank() }
            .map { it.split(',').toList().reversed() }.toList()
        val afterConstraints = constraints.groupBy(keySelector = { it.first }, valueTransform = { it.second })
        val beforeConstraints = constraints.groupBy(keySelector = { it.second }, valueTransform = { it.first })

        return updatesReveredOrder.filter { !isSafe(it, afterConstraints) }
            .map {
                it.sortedWith(comparator = { a, b ->
                    return@sortedWith compare(a, b, afterConstraints, beforeConstraints)
                })
            }
            //.filter { isSafePrint(it, afterConstraints) } // validation
            .sumOf { it[it.size / 2].toInt() }
    }

    val exampleInput = readInput("Day05_example")
    checkEquals(part1(exampleInput), 143)
    checkEquals(part2(exampleInput), 123)

    val input = readInput("Day05")
    "Solution part 1: ${part1(input)}".println()
    "Solution part 2: ${part2(input)}".println()
}
