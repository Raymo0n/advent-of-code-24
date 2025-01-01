import kotlin.math.abs

fun main() {

    fun getTwoList(input: List<String>) = input
        .map {
            val left = it.substringBefore(" ").toLong()
            val right = it.substringAfterLast(" ").toLong()
            left to right
        }.unzip()

    fun part1(input: List<String>): Long {
        val (left, right) = getTwoList(input)
        return left.sorted()
            .zip(right.sorted())
            .sumOf { (a, b) -> abs(a - b) }
    }

    fun part2(input: List<String>): Long {
        val (left, right) = getTwoList(input)

        val summary1 = left.groupingBy { it }.eachCount()
        val summary2 = right.groupingBy { it }.eachCount()

        return summary1.map { (num, amount) -> summary2[num]?.let { num * amount * it } ?: 0 }
            .sum()
    }

    val exampleInput = readInput("Day01_example")
    checkEquals(part1(exampleInput), 11L)
    checkEquals(part2(exampleInput), 31L)

    val input = readInput("Day01")
    "Solution part 1: ${part1(input)}".println()
    "Solution part 2: ${part2(input)}".println()
}
