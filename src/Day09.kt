private data class MemoryFragment(val index: Long, val length: Int, val value: Int) {

}

fun main() {

    fun part1(input: List<String>): Long {
        var memoryIndex = 0
        val diskMap = input[0].map { it.digitToInt() }
        val array = Array(size = diskMap.sum(), init = { -1L })
        diskMap.forEachIndexed { index, i ->
            if (index % 2 == 0) {
                for (i1 in 0..<i) {
                    array[memoryIndex++] = (index / 2).toLong()
                }
            } else {
                memoryIndex += i
            }
        }
        var lastIndex = array.size - 1
        for (i in array.indices) {
            if (lastIndex <= i) {
                break
            }
            if (array[i] == -1L) {
                while (array[lastIndex] == -1L) {
                    lastIndex--
                }
                array[i] = array[lastIndex]
                array[lastIndex] = -1
                lastIndex--
            }
        }
        return array.filter { it != -1L }.mapIndexed { index, i -> index * i }.sum()
    }

    fun part2(input: List<String>): Long {
        var memoryIndex = 0L
        val diskMap = input[0].map { it.digitToInt() }
        val usedFragments = mutableListOf<MemoryFragment>()
        val freeFragments = mutableListOf<MemoryFragment>()
        diskMap.forEachIndexed { index, i ->
            if (index % 2 == 0) {
                usedFragments.add(MemoryFragment(index = memoryIndex, length = i, value = index / 2))
            } else {
                freeFragments.add(MemoryFragment(index = memoryIndex, length = i, value = -1))
            }
            memoryIndex += i
        }
        return usedFragments.reversed().map {
            val free = freeFragments.firstOrNull { free -> free.length >= it.length }
            if (free == null || free.index > it.index) {
                it
            } else {
                val indexOf = freeFragments.indexOf(free)
                freeFragments.removeAt(indexOf)
                if (it.length != free.length) {
                    freeFragments.add(
                        indexOf,
                        MemoryFragment(index = free.index + it.length, length = free.length - it.length, value = -1)
                    )
                }
                MemoryFragment(index = free.index, length = it.length, value = it.value)
            }
        }.sumOf { (it.index..<it.index + it.length).sumOf { index -> index * it.value } }
    }

    val exampleInput = readInput("Day09_example")
    checkEquals(part1(exampleInput), 1928L)
    checkEquals(part2(exampleInput), 2858L)

    val input = readInput("Day09")
    "Solution part 1: ${part1(input)}".println()
    "Solution part 2: ${part2(input)}".println()
}