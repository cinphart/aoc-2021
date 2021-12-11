fun main() {

    fun around(max: Pair<Int, Int>, pos: Pair<Int, Int>): List<Pair<Int, Int>> {
        val (mx, my) = max
        val (x, y) = pos
        return (-1..1).flatMap { dx ->
            (-1..1).map { dy ->
                Pair(dx, dy)
            }
        }.filter { (dx, dy) -> dx != 0 || dy != 0 }.map { (dx, dy) ->
            Pair(x + dx, y + dy)
        }.filter { (x1, y1) ->
            x1 >= 0 && y1 >= 0 && x1 < mx && y1 < my
        }
    }

    fun countFlashes(state: List<MutableList<Int>>): Int {
        var flashesSeen = 0
        var flashesAt = mutableSetOf<Pair<Int, Int>>()
        var size = Pair(state[0].size, state.size)
        do {
            flashesAt.clear()
            state.indices.forEach { y ->
                state[y].indices.forEach { x ->
                    if (state[y][x] > 9) {
                        state[y][x] = 0
                        flashesAt.add(Pair(x, y))
                        flashesSeen++
                    }
                }
            }
            flashesAt.forEach { at ->
                around(size, at).filter { (x, y) ->
                    state[y][x] != 0 && state[y][x] < 10
                }.forEach { (x, y) ->
                    state[y][x]++
                }
            }

        } while (flashesAt.size > 0)
        return flashesSeen
    }

    fun part1(input: List<String>): Int {
        var state = input.map {
            it.map {
                Integer.parseInt("${it}")
            } as MutableList<Int>
        }

        var totalFlashes = 0

        (0..99).forEach {
            state.indices.forEach { y ->
                state[y].indices.forEach { x ->
                    state[y][x]++
                }
            }

            totalFlashes += countFlashes(state)
        }

        return totalFlashes
    }

    fun part2(input: List<String>): Int {
        var state = input.map {
            it.map {
                Integer.parseInt("${it}")
            } as MutableList<Int>
        }

        var step = 0
        var flashes = 0
        do {
            step++
            state.indices.forEach { y ->
                state[y].indices.forEach { x ->
                    state[y][x]++
                }
            }
            flashes = countFlashes(state)
        } while (flashes < 100)

        return step
    }


// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)

    val input = readInput("Day11")
    println(part1(input))

    check(part2(testInput) == 195)
    println(part2(input))
}
