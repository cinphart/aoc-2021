enum class Fold {
    HORIZONTAL,
    VERTICAL
}

fun main() {

    val coord = Regex("(\\d+),(\\d+)")
    val fold = Regex("fold along (x|y)=(\\d+)")

    fun String.toFold(): Fold = if (this == "x") Fold.HORIZONTAL else Fold.VERTICAL

    fun loadIt(input: List<String>): Pair<Set<Pair<Int, Int>>, List<Pair<Fold, Int>>> {
        val coords = input.map {
            coord.find(it)?.let { m ->
                Integer.parseInt(m.groupValues[1]) to Integer.parseInt(m.groupValues[2])
            }
        }.filterNotNull().toSet()

        val folds = input.map {
            fold.find(it)?.let { m ->
                (m.groupValues[1].toFold()) to (Integer.parseInt(m.groupValues[2]))
            }
        }.filterNotNull()

        return coords to folds
    }

    fun Pair<Fold, Int>.translate(p: Pair<Int, Int>): Pair<Int, Int> {
        val (x, y) = p
        val (fold, at) = this

        val result = if (fold == Fold.HORIZONTAL) {
            if (x > at) {
                (at - (x - at)) to y
            } else {
                x to y
            }
        } else {
            if (y > at) {
                x to (at - (y - at))
            } else {
                x to y
            }
        }
        return result
    }

    fun part1(input: List<String>): Int {
        val (coords, folds) = loadIt(input)

        val folded = coords.map {
            folds[0].translate(it)
        }.toSet()

        return folded.size
    }

    fun part2(input: List<String>): List<String> {
        var (coords, folds) = loadIt(input)

        folds.forEach { f ->
            coords = coords.map { p ->
                f.translate(p)
            }.toSet()
        }

        val maxX = coords.maxOf { it.first }
        val maxY = coords.maxOf { it.second }

        return (0..maxY).map { y ->
            (0..maxX).map { x ->
                if ((x to y) in coords) '#' else '.'
            }.joinToString("")
        }
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)

    val input = readInput("Day13")
    println(part1(input))

    println(part2(testInput).joinToString("\n"))
    println(part2(input).joinToString("\n"))
}
