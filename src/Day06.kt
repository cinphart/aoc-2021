import kotlin.math.max
import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Int {
        var counts =
                input[0].splitToInts().groupBy { it }.entries.map { e -> e.key to e.value.size }.toMap()

        (0..79).forEach {
            counts = mapOf(
                    0 to (counts[1]?:0),
                    1 to (counts[2]?:0),
                    2 to (counts[3]?:0),
                    3 to (counts[4]?:0),
                    4 to (counts[5]?:0),
                    5 to (counts[6]?:0),
                    6 to ((counts[0]?:0)+(counts[7]?:0)),
                    7 to (counts[8]?:0),
                    8 to (counts[0]?:0)
            )
            println("Iteration $it - count ${counts.values.sum()}")
        }
        return counts.values.sum()
    }

    fun part2(input: List<String>): Long {
        var counts =
                input[0].splitToInts().groupBy { it }.entries.map { e -> e.key to e.value.size.toLong() }.toMap()

        (0..255).forEach {
            counts = mapOf(
                    0 to (counts[1]?:0),
                    1 to (counts[2]?:0),
                    2 to (counts[3]?:0),
                    3 to (counts[4]?:0),
                    4 to (counts[5]?:0),
                    5 to (counts[6]?:0),
                    6 to ((counts[0]?:0)+(counts[7]?:0)),
                    7 to (counts[8]?:0),
                    8 to (counts[0]?:0)
            )
            println("Iteration $it - count ${counts.values.sum()}")
        }
        return counts.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539L)


    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
