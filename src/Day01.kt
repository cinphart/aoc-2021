fun main() {
    fun List<String>.asInts(): List<Int> {
        return map { a -> Integer.parseInt(a) }
    }

    fun List<Int>.countDecreases(): Int {
        return windowed(2).fold(0) { acc, e ->
            if (e[0] < e[1]) acc + 1 else acc
        }
    }

    fun part1(asInts: List<Int>): Int {
        return asInts.countDecreases()
    }

    fun part2(input: List<Int>): Int {
        return input.windowed(3).map {
            it[0] + it[1] + it[2]
        }.countDecreases()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput.asInts()) == 7)
    check(part2(testInput.asInts()) == 5)


    val input = readInput("Day01")
    println(part1(input.asInts()))
    println(part2(input.asInts()))
}
