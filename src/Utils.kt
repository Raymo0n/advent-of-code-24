import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun checkEquals(value: Any, expected: Any) {
    check(value == expected) { println("$value but should be $expected") }
}

typealias Location = Pair<Int, Int>
typealias Grid<T> = Array<Array<T>>

fun Location.north(): Location {
    return Location(this.first - 1, this.second)
}

fun Location.east(): Location {
    return Location(this.first, this.second + 1)
}

fun Location.south(): Location {
    return Location(this.first + 1, this.second)
}

fun Location.west(): Location {
    return Location(this.first, this.second - 1)
}

operator fun <T> Grid<T>.get(value: Location): T {
    return this[value.first][value.second]
}

operator fun <T> Grid<T>.set(index: Location, value: T) {
    this[index.first][index.second] = value
}

fun <T> Grid<T>.isOutOfBounds(value: Location): Boolean {
    return value.first < 0 || value.second < 0 || value.first >= this.size || if (this.isEmpty()) true else value.second >= this[0].size
}
