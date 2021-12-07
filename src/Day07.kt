import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        var initial = input[0].splitToInts().groupBy { it }.map { it.key to it.value.size }.toMap()
        var l = initial.keys.minOrNull()!!
        var h = initial.keys.maxOrNull()!!

        var d = (l..h).map { c ->
            c to initial.keys.fold(0) { a, b ->
                a + (initial[b] ?: 0) * abs(c - b)
            }
        }.toMap()

        d.map {
            println("${it.key} -> ${it.value}")
        }

        return d.values.minOrNull()!!
    }

    fun fuel(m : Int) = (1..m).sum()

    fun part2(input: List<String>): Int {
        var initial = input[0].splitToInts().groupBy { it }.map { it.key to it.value.size }.toMap()
        var l = initial.keys.minOrNull()!!
        var h = initial.keys.maxOrNull()!!

        var d = (l..h).map { c ->
            c to initial.keys.fold(0) { a, b ->
                a + (initial[b] ?: 0) * fuel(abs(c - b))
            }
        }.toMap()

        d.map {
            println("${it.key} -> ${it.value}")
        }

        return d.values.minOrNull()!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)


    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
