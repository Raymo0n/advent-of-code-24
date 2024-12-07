import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        input.forEach {
            val split = it.trim().split(" ", limit = 2);
            list1.add(split[0].toInt());
            list2.add(split[1].trim().toInt());
        }
        list1.sort()
        list2.sort()
        var diff = 0;
        for (i in list1.indices) {
            diff += abs(list1[i] - list2[i])
        }

        return diff
    }

    fun part2(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        input.forEach {
            val split = it.trim().split(" ", limit = 2);
            list1.add(split[0].toInt());
            list2.add(split[1].trim().toInt());
        }
        val summary1 = list1.groupBy { it }.mapValues { it.value.size }
        val summary2 = list2.groupBy { it }.mapValues { it.value.size }

        var similarity = 0
        for (i in summary1) {
            similarity += summary2[i.key]?.let { i.key * it * i.value } ?: 0
        }
        return similarity
    }

    val input1 = """3   4
                4   3
                2   5
                1   3
                3   9
                3   3""".split("\n")
    check(part1(input1) == 11)
    check(part2(input1) == 31)

    val input = readInput("Day1")
    part1(input).println()
    part2(input).println()
}
