fun main() {
    fun List<String>.asInts() : List<Int> {
        return map { a -> Integer.parseInt(a) }
    }

    fun List<Int>.countDecreases() : Int {
        return drop(1).fold(Pair(0, first())) { acc, e ->
            Pair((if (e > acc.second) acc . first +1 else acc.first), e)
        }.first
    }

    fun part1(asInts: List<Int>): Int {
        return asInts.countDecreases()
    }

    fun part2(input: List<Int>): Int {
        return input.drop(2).mapIndexed {
            i, v -> v + input[i] + input[i+1]
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
