import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

fun main() {

    fun Long.blink(): List<Long> {
        if (this == 0L) {
            return listOf(1)
        } else if (this.toString().length % 2 == 0) {
            val toString = this.toString()
            return listOf(
                toString.substring(0, toString.length / 2).toLong(),
                toString.substring(toString.length / 2).toLong()
            )
        } else {
            return listOf(this * 2024L)
        }
    }

    fun getBlinkResult(value: Long, blinks: Int, cache: MutableMap<Pair<Long, Int>, Long>): Long {
        cache[value to blinks]?.let { return it }
        if (blinks == 1) {
            return value.blink()
                .count()
                .toLong()
                .also { cache[value to blinks] = it }
        }

        return value.blink().sumOf { getBlinkResult(it, blinks - 1, cache) }
            .also { cache[value to blinks] = it }
    }

    fun part1(input: List<String>): Long {
        val cache = mutableMapOf<Pair<Long, Int>, Long>()
        val map = input[0].split(' ').map { it.toLong() }
        return map.sumOf { getBlinkResult(value = it, blinks = 25, cache = cache) }
    }

    fun part2(input: List<String>): Long {
        val cache = mutableMapOf<Pair<Long, Int>, Long>()
        val map = input[0].split(' ').map { it.toLong() }
        return map.sumOf { getBlinkResult(value = it, blinks = 75, cache = cache) }
    }

    val exampleInput = readInput("Day11_example")
    checkEquals(part1(exampleInput), 55312L)
    //checkEquals(part2(exampleInput), 0L)

    val input = readInput("Day11")
    "Solution part 1: ${part1(input)}".println()
    "Solution part 2: ${part2(input)}".println()
}