data class Board(val rows: List<List<Int>>) {
    val rowClearCount = mutableMapOf<Int, Int>().withDefault { 0 }
    val colClearCount = mutableMapOf<Int, Int>().withDefault { 0 }
    val cleared = mutableSetOf<Pair<Int, Int>>()
    override fun toString(): String {
        return rows.mapIndexed { row, numbers ->
            numbers.mapIndexed {
                col, value -> if (Pair(row,col) in cleared) "XX" else "%2d".format(value)
            }.joinToString(" ")
        }.joinToString { "\n" }
    }
}

data class Game(
    val numbers: List<Int>,
    val boards: List<Board>
)

fun main() {

    fun ints(s: String, sep: String = " ") =
        s.trim().split(sep).map { it.trim() }.filter { it.isNotEmpty() }.map { Integer.parseInt(it) }


    fun parseGame(input: List<String>) =
        Game(
            numbers = ints(input[0], ","),
            boards = input.drop(1).chunked(6) { s ->
                Board(rows = s.drop(1).map { l -> ints(l) })
            }
        )


    fun processTurn(called: Int, board: Board): Boolean {
        board.rows.forEachIndexed { row, numbers ->
            numbers.forEachIndexed { col, number ->
                if (number == called) {
                    board.rowClearCount[row] = (board.rowClearCount[row]?:0) + 1
                    board.colClearCount[col] = (board.colClearCount[col]?:0) + 1
                    board.cleared.add(Pair(row, col))
                    return board.rowClearCount[row] == 5 || board.colClearCount[col] == 5
                }
            }
        }
        return false
    }

    fun boardScore(board: Board?): Int {
        var sum = 0
        board?.rows?.forEachIndexed { row, numbers ->
            numbers.forEachIndexed { col, n ->
                if (Pair(row, col) !in board.cleared) {
                    sum += n
                }
            }
        }
        return sum
    }

    fun part1(input: List<String>): Int {
        val g = parseGame(input)

        var boardCleared: Board? = null

        val called = g.numbers.find { ball ->
            g.boards.forEachIndexed { index, board ->
                if (processTurn(ball, board)) {
                    boardCleared = board
                }
            }
            boardCleared != null
        } ?: 0

        val unmatchedScore = boardScore(boardCleared)
        println("${called} x ${unmatchedScore}")

        return called * unmatchedScore
    }

    fun part2(input: List<String>): Int {
        val g = parseGame(input)

        var boardCleared: Board? = null

        var won = mutableSetOf<Int>()
        val called = g.numbers.find { ball ->
            g.boards.forEachIndexed { index, board ->
                if (index !in won && processTurn(ball, board)) {
                    won.add(index)
                    boardCleared = board
                }
            }
            won.size == g.boards.size
        } ?: 0

        val unmatchedScore = boardScore(boardCleared)
        println("${called} x ${unmatchedScore}")

        return called * unmatchedScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)


    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
