fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)


    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
