fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 15)

    val input = readInput("Day10")
    println(part1(input))

    check(part2(testInput) == 1134)
    println(part2(input))
}