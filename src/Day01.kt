import kotlin.math.abs

fun main() {

    fun getTwoList(input: List<String>) = input
        .map { it.trim() }
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

    val input1 = """3   4
                4   3
                2   5
                1   3
                3   9
                3   3""".split("\n")
    check(part1(input1) == 11L)
    check(part2(input1) == 31L)

    val input = readInput("Day1")
    part1(input).println()
    part2(input).println()
}
