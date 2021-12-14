fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day15_test")
    check(part1(testInput) == 10)

    val input = readInput("Day15")
    println(part1(input))

    check(part2(testInput) == 36)
    println(part2(input))
}
