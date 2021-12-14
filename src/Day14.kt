val memo = mutableMapOf<Pair<Pair<Char, Char>, Int>, Map<Char, Long>>()

fun main() {
    val rule = Regex("(.)(.) -> (.)")


    fun processRules(result: String, rules: Map<Pair<Char, Char>, String>): String {
        return result.windowed(2).map { it[0] to it[1] }.fold(result.substring(0, 1)) { a, p ->
            a + (rules[p] ?: "") + p.second
        }
    }

    fun part1(input: List<String>): Int {
        val start = input[0]!!
        val rules = input.drop(2).map {
            rule.find(it)?.groupValues?.let {
                (it[1]!![0] to it[2]!![0]) to (it[3]!!)
            }!!
        }.toMap()

        var result = start
        (0..10).forEach {
            result = processRules(result, rules)
        }

        val occurrences = result.groupBy { it }.mapValues { it.value.size }

        return occurrences.values.maxOf { it } - occurrences.values.minOf { it }
    }

    fun mm(m1: Map<Char, Long>, m2: Map<Char, Long>, c: Char): Map<Char, Long> {
        val result = (m1.keys + m2.keys).associateWith {
            ((m1[it] ?: 0) + (m2[it] ?: 0)) - if (it == c) 1 else 0
        }
        return result
    }

    fun part2(input: List<String>, depth: Int): Long {
        val start = input[0]!!
        val rules = input.drop(2).map {
            rule.find(it)?.groupValues?.let {
                (it[1]!![0] to it[2]!![0]) to (it[3]!![0])
            }!!
        }.toMap()

        var ruleOccurrences = rules.map {
            it.key to it.key.occurrences()
        }.toMap()

        (0..depth-1).forEach { depth ->
            ruleOccurrences = ruleOccurrences.map {
                val key = it.key
                val insert: Char = rules[key]!!
                val m1 = ruleOccurrences[it.key.first to insert]!!
                val m2 = ruleOccurrences[insert to it.key.second]!!
                it.key to mm(m1, m2, insert)
            }.toMap()
        }

        val occurrences = start.windowed(2).map { it[0] to it[1] }
            .fold(mapOf<Char, Long>(start[0] to 1)) { acc, p ->
                mm(acc, ruleOccurrences[p]!!, p.first)
            }

        return occurrences.values.maxOf { it } - occurrences.values.minOf { it }
    }

    val testInput = readInput("Day14_test")
    check(part2(testInput, 10) == 1588L)

    val input = readInput("Day14")
    println(part2(input, 10))

    check(part2(testInput, 40) == 2188189693529L)
    println(part2(input, 40))
}
