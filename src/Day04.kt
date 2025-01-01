fun main() {

    fun count(str: String, search: String): Int {
        var count = 0
        var indexOf = str.indexOf(search)
        while (indexOf != -1) {
            count++
            indexOf = str.indexOf(search, startIndex = indexOf + 1)
        }
        return count
    }

    fun countXmas(it: String): Int {
        return count(it, "XMAS") + count(it, "SAMX")
    }

    fun isXmas(array: Array<Array<Char>>, row: Int, column: Int): Boolean {
        if (row == 0 || column == 0 || row == array.size - 1 || column == array[0].size - 1) {
            return false
        }
        return (array[row - 1][column - 1].toString() + array[row + 1][column + 1].toString()).let { it == "MS" || it == "SM" }
                &&
                (array[row + 1][column - 1].toString() + array[row - 1][column + 1].toString()).let { it == "MS" || it == "SM" }
    }

    fun part1(input: List<String>): Int {
        val diagonal = mutableMapOf<Int, StringBuilder>()
        val diagonalReverse = mutableMapOf<Int, StringBuilder>()
        val vertical = mutableMapOf<Int, StringBuilder>()
        input.forEachIndexed { row, s ->
            s.forEachIndexed { column, c ->
                val buildDiagonal = diagonal.computeIfAbsent(column - row + input.size) { StringBuilder() }
                buildDiagonal.append(c)
                val buildDiagonalReverse = diagonalReverse.computeIfAbsent(column + row) { StringBuilder() }
                buildDiagonalReverse.append(c)
                val buildVertical = vertical.computeIfAbsent(column) { StringBuilder() }
                buildVertical.append(c)
            }
        }
        return (input.sumOf { countXmas(it) }
                + diagonal.values.sumOf { countXmas(it.toString()) }
                + diagonalReverse.values.sumOf { countXmas(it.toString()) }
                + vertical.values.sumOf { countXmas(it.toString()) })
    }

    fun part2(input: List<String>): Int {
        val array = Array(size = input.size, init = { Array(size = input[0].length, init = { ' ' }) })
        input.forEachIndexed { row, s ->
            s.forEachIndexed { column, c ->
                array[row][column] = c
            }
        }

        return array.mapIndexed { row, chars ->
            chars.filterIndexed { column, c ->
                if (c == 'A') {
                    isXmas(array, row, column)
                } else {
                    false
                }
            }.count()
        }.sum()
    }

    val exampleInput = readInput("Day04_example")
    checkEquals(part1(exampleInput), 18)
    checkEquals(part2(exampleInput), 9)

    val input = readInput("Day04")
    "Solution part 1: ${part1(input)}".println()
    "Solution part 2: ${part2(input)}".println()
}