fun main() {

    fun mostCommonBit(input: List<String>): BooleanArray {
        val bits = mutableMapOf<Int, Int>()

        input.forEach {
            it.forEachIndexed { i, ch ->
                val current = bits[i] ?: 0
                when (ch) {
                    '0' -> {
                        bits[i] = current - 1
                    }
                    '1' -> {
                        bits[i] = current + 1
                    }
                    else -> {
                        bits[i] = current
                    }
                }
            }
        }

        val result = BooleanArray(bits.size)
        bits.entries.map { (k,v) ->
            result[k] = v >= 0
        }
        return result
    }

    fun part1(input: List<String>): Int {
        val bits = mostCommonBit(input)

        val gamma = bits.foldIndexed(0) { k, acc, v ->
            if (v) acc + (1 shl (bits.size - k - 1)) else acc
        }

        val epsilon = bits.foldIndexed(0) { k, acc, v ->
            if (!v) acc + (1 shl (bits.size - k - 1)) else acc
        }

        println("Gamma: ${gamma} Epsilon: ${epsilon}")

        return gamma * epsilon
    }

    fun bitCriteria(criteria: Boolean, input: List<String>, position: Int = 0) : List<String> {
        val bits = mostCommonBit(input)

        val matchingCriteria = input.filter { s -> (s[position] == '1')  == (bits[position] == criteria) }

        println("${criteria}/${position} - ${bits[position]}: ${matchingCriteria}")

        if (matchingCriteria.size == 1) return matchingCriteria

        return bitCriteria(criteria, matchingCriteria, position+1)
    }


    fun part2(input: List<String>): Int {
        val oxygen = Integer.parseInt(bitCriteria(true, input)[0],2)
        val co2 = Integer.parseInt(bitCriteria(false, input)[0],2)
        println("oxygen: ${oxygen}, co2: ${co2}")
        return oxygen * co2;
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)


    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
