fun main() {

    fun pt(input: List<List<Int>>, x: Int, y: Int): Int {
        if (x < 0 || y < 0 || y >= input.size || x >= input[y].size) {
            return -1;
        }
        return input[y][x]
    }

    val offsets = arrayOf(0 to 1, 1 to 0, -1 to 0, 0 to -1);

    fun lowPoint(terrain: List<List<Int>>, x: Int, y: Int): Boolean =
        offsets.all { (dx, dy) ->
            pt(terrain, x + dx, y + dy).let {
                it == -1 || it > terrain[y][x]
            }
        }

    fun part1(input: List<String>): Int {
        val terrain = input.digitArray()
        return terrain.indices.flatMap { y ->
            terrain[y].indices.map { x ->
                val curr = pt(terrain, x, y)
                if (lowPoint(terrain, x, y))
                    curr + 1
                else
                    0
            }
        }.sum()
    }

    fun basinSize(terrain: List<List<Int>>, x: Int, y: Int): Int {
        var processed = mutableSetOf<Pair<Int, Int>>(x to y)
        var toProcess = setOf(x to y)

        while (toProcess.size > 0) {
            toProcess = toProcess.flatMap { (x, y) ->
                offsets.map { (dx, dy) ->
                    ((x + dx) to (y + dy)) to pt(terrain, x, y)
                }
            }.filter { (pos, level) ->
                !processed.contains(pos) && pt(terrain, pos.first, pos.second).let { it > level && it != 9 }
            }.map { (pos, _) ->
                pos
            }.toSet()
            processed += toProcess
        }
        return processed.size
    }

    fun part2(input: List<String>): Int {
        val terrain = input.digitArray()

        val basinSizes = terrain.indices.flatMap { y ->
            terrain[y].indices.map { x ->
                x to y
            }.filter { (x, y) ->
                lowPoint(terrain, x, y)
            }.map { (x, y) ->
                basinSize(terrain, x, y)
            }
        }

        return basinSizes.sorted().takeLast(3).let {
            l -> l[0] * l[1] * l[2]
        }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
