fun main() {

    fun pt(input: List<String>, x: Int, y: Int): Int {
        if (x < 0 || y < 0 || y >= input.size || x >= input[y].length) {
            return -1;
        }
        return Integer.parseInt("${input[y][x]}")
    }

    val offsets = arrayOf(0 to 1, 1 to 0, -1 to 0, 0 to -1);

    fun part1(input: List<String>): Int {
        return input.indices.flatMap { y ->
            input[y].indices.map { x ->
                val curr  = pt(input, x,y)
                if (offsets.all { (dx, dy) ->
                        pt(input, x + dx, y + dy).let {
                            it == -1 || it > curr
                        }
                    })
                    curr + 1
                else
                    0
            }
        }.sum()
    }


    fun part2(input: List<String>): Int {
        return input.size
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
//check(part2(testInput) == 61229)


    val input = readInput("Day09")
    println(part1(input))
//println(part2(input))
}
