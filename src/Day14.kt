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
            println(result)
        }

        val occurrences = result.groupBy { it }.mapValues { it.value.size }

        println("$occurrences")

        return occurrences.values.maxOf { it } - occurrences.values.minOf { it }
    }

    fun mm(m1: Map<Char, Long>, m2: Map<Char, Long>): Map<Char, Long> {
        return (m1.keys + m2.keys).associateWith {
            ((m1[it] ?: 0) + (m2[it] ?: 0))
        }
    }

    fun part2(input: List<String>, depth: Int): Long {
        val start = input[0]!!
        val rules = input.drop(2).map {
            rule.find(it)?.groupValues?.let {
                (it[1]!![0] to it[2]!![0]) to (it[3]!![0])
            }!!
        }.toMap()

        var ruleOccurrences = rules.map {
            it.key to (mapOf<Char, Long>(it.key.second to 1L))
        }.toMap()

        (0..depth).forEach {
            ruleOccurrences = ruleOccurrences.map {
                val key = it.key
                val insert: Char = rules[key]!!
                val m1 = ruleOccurrences[it.key.first to insert]!!
                val m2 = ruleOccurrences[insert to it.key.first]!!
                it.key to mm(m1, m2)
            }.toMap()
        }

        val occurrences = start.windowed(2).map { it[0] to it[1] }
            .fold(mapOf<Char, Long>(start[0] to 1)) { acc, p ->
                mm(acc, ruleOccurrences[p]!!)
            }

        println("${occurrences}")

        println(occurrences.values.maxOf { it })
        println(occurrences.values.minOf { it })
        println(occurrences.values.maxOf { it } - occurrences.values.minOf { it })

        return occurrences.values.maxOf { it } - occurrences.values.minOf { it }
    }

    val testInput = readInput("Day14_test")
    part2(testInput, 1)
    part2(testInput, 2)
    part2(testInput, 3)
    check(part2(testInput, 10) == 1588L)

    val input = readInput("Day14")
    println(part2(input, 10))

    check(part2(testInput, 40) == 2188189693529L)
    println(part2(input, 40))
}
