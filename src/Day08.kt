import java.util.*

fun main() {

    fun part1(input: List<String>): Int {
        return input.fold(0) { a, s ->
            a + if (s.length > 0)
                s.split('|')[1].split(' ').filter { it.length in arrayOf(2, 3, 4, 7) }.count()
            else 0
        }
    }


    fun normalizeString(input: String): SortedSet<Char> {
        return input.toSortedSet()
    }

    //   a b c d e f g
    // 0 1 1 1 0 1 1 1 / 6
    // 1 0 0 1 0 0 1 0 / 2
    // 2 1 0 1 1 1 0 1 / 5
    // 3 1 0 1 1 0 1 1 / 5
    // 4 0 1 1 1 0 1 0 / 4
    // 5 1 1 0 1 0 1 1 / 5
    // 6 1 1 0 1 1 1 1 / 6
    // 7 1 0 1 0 0 1 0 / 3
    // 8 1 1 1 1 1 1 1 / 7
    // 9 1 1 1 1 0 1 1 / 6
    // 1, 4, 7, 8 are easy
    // 6 = 6 segments, not all segments from 1, allows determining 'f' segment (in 1, in 6) and 'c' segment (in 6, not in 1)
    // 3 = 5 segments, contains everything in '1'
    // 2 = 5 segments, contains 'c', not 3
    // 5 = 5 segments, contains 'f', not 3
    // 9 = 6 segments, everything in 5 + 'c'
    // 0 remains
    fun determineDigits(input: List<SortedSet<Char>>): Map<SortedSet<Char>, Int> {
        val one = input.find { it.size == 2 }!!
        val seven = input.find { it.size == 3 }!!
        val four = input.find { it.size == 4 }!!
        val eight = input.find { it.size == 7 }!!
        val len6 = input.filter { it.size == 6 }
        val six = len6.find { !it.containsAll(one) }!!
        val segf = six.find { one.contains(it) }!!
        val segc = one.find { !six.contains(it) }!!
        val len5 = input.filter { it.size == 5 }
        val three = len5.find { it.containsAll(one) }!!
        val two = len5.find { it.contains(segc) && !it.contains(segf) }!!
        val five = len5.find { !it.contains(segc) && it.contains(segf) }!!
        val nine = len6.find { it.containsAll(five) && it.contains(segc) }!!
        val zero = len6.find { it != six && it != nine }!!

        return mapOf(
            one to 1,
            two to 2,
            three to 3,
            four to 4,
            five to 5,
            six to 6,
            seven to 7,
            eight to 8,
            nine to 9,
            zero to 0
        )
    }

    fun determineResult(mapping: Map<SortedSet<Char>, Int>, digits: List<SortedSet<Char>>): Int {
        return digits.fold(0) { a, dd ->
            a * 10 + mapping[dd]!!
        }
    }

    fun String.splitIgnoreEmpty(d: String): List<String> {
        return this.split(d).filter { s -> s.isNotEmpty() }
    }

    fun part2(input: List<String>): Int {

        return input.map { s -> s.split("|") }.fold(0) { a, s ->
            a + determineResult(
                determineDigits(s[0].splitIgnoreEmpty(" ").map { s -> normalizeString(s) }),
                s[1].splitIgnoreEmpty(" ").map { s -> normalizeString(s) }
            )
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)


    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
