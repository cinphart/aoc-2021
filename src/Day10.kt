fun main() {

    val invalidScores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    val incompleteScores = mapOf(
        ')' to 1L,
        ']' to 2L,
        '}' to 3L,
        '>' to 4L
    )

    val expect = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )

    fun findInvalidPairs(l: String, expected: Char) : Pair<Int, String> {
        if (l.isEmpty()) return Pair(0, l)
        var rest = l.drop(1)
        var lookingAt = l[0]
        while (lookingAt in expect.keys) {
            val (score, left) = findInvalidPairs(rest, expect[lookingAt]!!)
            if (score != 0) return Pair(score, "")
            if (left.isEmpty()) return Pair(0, "")
            rest = left.drop(1)
            lookingAt = left[0]
        }
        if (lookingAt == expected) return Pair(0, rest)
        return Pair(invalidScores[lookingAt]?:0,"")
    }

    fun scoreInvalidLine(l: String): Int =
        findInvalidPairs(l.drop(1), expect[l[0]]!!).first

    fun part1(input: List<String>): Int {
        return input.map { scoreInvalidLine(it) }.sum()
    }

    fun findIncompletePairs(l: String, expected: Char) : Pair<String, String> {
        if (l.isEmpty()) return Pair("${expected}", "")
        var rest = l.drop(1)
        var lookingAt = l[0]
        while (lookingAt in expect.keys) {
            val (missing, left) = findIncompletePairs(rest, expect[lookingAt]!!)
            if (!missing.isEmpty()) return Pair("${missing}${expected}", "")
            if (left.isEmpty()) return Pair("${expected}", "")
            rest = left.drop(1)
            lookingAt = left[0]
        }
        if (lookingAt == expected) return Pair("", rest)
        // Unexpected condition!
        check("should not" == "get here")
        return Pair("","")
    }


    fun scoreIncompleteLine(l : String): Long {
        var left = l
        var incomplete = ""
        do {
            val c = findIncompletePairs(left.drop(1), expect[left[0]]!!)
            left = c.second
            incomplete = c.first
        } while (!left.isEmpty())
        return incomplete.fold(0L) {
            a, c -> a*5L + incompleteScores[c]!!
        }
    }

    fun part2(input: List<String>): Long {
        val scores = input.filter {scoreInvalidLine(it) == 0 }.map { scoreIncompleteLine(it)}.sorted()
        return scores[scores.size/2]
    }

    check(scoreInvalidLine("([][]>") == 25137)

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)

    val input = readInput("Day10")
    println(part1(input))

    check(part2(testInput) == 288957L)
    println(part2(input))
}
