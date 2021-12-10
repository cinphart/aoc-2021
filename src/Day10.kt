fun main() {

    val scores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    val expect = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )

    fun matchPairs(l: String, expected: Char) : Pair<Int, String> {
        //println("expecting: ${expected}, input: $l")
        if (l.isEmpty()) return Pair(0, l)
        var rest = l.drop(1)
        var lookingAt = l[0]
        while (lookingAt in expect.keys) {
            //println("got new start ${lookingAt}")
            val (score, left) = matchPairs(rest, expect[lookingAt]!!)
            if (score != 0) return Pair(score, "")
            if (left.isEmpty()) return Pair(0, "")
            rest = left.drop(1)
            lookingAt = left[0]
        }
        //println("matching ${lookingAt}:${rest} to ${expected}")
        if (lookingAt == expected) return Pair(0, rest)
        return Pair(scores[lookingAt]?:0,"")
    }

    fun scoreInvalidLine(l: String): Int =
        matchPairs(l.drop(1), expect[l[0]]!!).first.apply {
            println("Score: ${l} = ${this}")
        }

    fun part1(input: List<String>): Int {
        return input.map { scoreInvalidLine(it) }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    check(scoreInvalidLine("([][]>") == 25137)

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)

    val input = readInput("Day10")
    println(part1(input))

    check(part2(testInput) == 288957)
    println(part2(input))
}
