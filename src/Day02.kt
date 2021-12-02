import java.util.regex.Pattern

enum class Direction {
    FORWARD,
    DOWN,
    UP;
};

fun main() {

    val parser = Regex("(forward|down|up) *(\\d+)")

    val matches = mapOf(
        "forward" to Direction.FORWARD,
        "down" to Direction.DOWN,
        "up" to Direction.UP
    )

    fun List<String>.parsed(): List<Pair<Direction, Int>?> {
        return map { a ->
            parser.find(a)?.let { parts ->
                Pair(
                    matches[parts.groupValues[1]!!]!!,
                    Integer.parseInt(parts.groupValues[2]!!)
                )
            }
        }
    }

    fun List<Int>.countDecreases(): Int {
        return drop(1).fold(Pair(0, first())) { acc, e ->
            Pair((if (e > acc.second) acc.first + 1 else acc.first), e)
        }.first
    }

    fun part1(asDirs: List<Pair<Direction, Int>?>): Int {
        var result = asDirs.fold(Pair(0, 0)) { acc: Pair<Int, Int>, e: Pair<Direction, Int>? ->
            e?.let {
                when (e.first) {
                    Direction.FORWARD -> Pair(acc.first + e.second, acc.second)
                    Direction.DOWN -> Pair(acc.first, acc.second + e.second)
                    Direction.UP -> Pair(acc.first, acc.second - e.second)
                }
            } ?: acc
        }
        println(result)
        return result.first * result.second
    }

    fun part2(asDirs: List<Pair<Direction, Int>?>): Int {
        var result = asDirs.fold(Triple(0, 0, 0)) { acc: Triple<Int, Int, Int>, e: Pair<Direction, Int>? ->
            e?.let {
                when (e.first) {
                    Direction.FORWARD -> Triple(acc.first, acc.second + e.second, acc.third + acc.first * e.second)
                    Direction.DOWN -> Triple(acc.first + e.second, acc.second, acc.third)
                    Direction.UP -> Triple(acc.first - e.second, acc.second, acc.third)
                }
            } ?: acc
        }
        println(result)
        return result.second * result.third
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput.parsed()) == 150)
    check(part2(testInput.parsed()) == 900)


    val input = readInput("Day02")
    println(part1(input.parsed()))
    println(part2(input.parsed()))
}
