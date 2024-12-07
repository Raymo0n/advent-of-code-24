fun main() {

    fun descending(nums: Pair<Int, Int>) = (nums.first - nums.second) in 1..3
    fun ascending(nums: Pair<Int, Int>) = (nums.second - nums.first) in 1..3

    fun isLineSafe(report: List<Int>): Boolean {
        val base = report.subList(fromIndex = 1, toIndex = report.size)
        val shifted = report.dropLast(1)
        val zip = base.zip(shifted)
        val descendingCount = zip.takeWhile { descending(it) }.count()
        return if (descendingCount == zip.size) {
            true
        } else if (descendingCount == 0
            && zip.takeWhile { ascending(it) }.count() == zip.size
        ) {
            true
        } else {
            false
        }
    }

    fun part1(input: List<String>): Int {
        return input.count {
            val report = it.split(" ").map { it.toInt() }
            isLineSafe(report)
        }
    }

    fun part2(input: List<String>): Int {
        return input.count {
            val report = it.split(" ").map { it.toInt() }
            var safe = false
            for (i in report.indices) {
                if (isLineSafe(report.toMutableList().apply { removeAt(i) })) {
                    safe = true
                    break
                }
            }
            safe
        }
    }

    val exampleInput = readInput("Day02_example")
    check(part1(exampleInput) == 2)
    check(part2(exampleInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
