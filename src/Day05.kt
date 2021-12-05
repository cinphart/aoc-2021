import kotlin.math.max
import kotlin.math.min

fun main() {

    fun coords(input : String) : Pair<Int, Int> {
        return input.split(",").map {
            Integer.parseInt(it)
        }.let {
            it[0] to it[1]
        }
    }

    fun MutableMap<Pair<Int,Int>,Int>.p(x: Int, y: Int) {
        val k = Pair(x,y)
        put(k, (get(k)?:0) + 1)
    }

    fun part1(input: List<String>): Int {
        val m = mutableMapOf<Pair<Int,Int>,Int>().withDefault { 0 }
        input.map {
            val q = it.split(" ")
            coords(q[0]) to coords(q[2])
        }.forEach { r ->
            if (r.first.first == r.second.first) {
                val x = r.first.first
                val y1 = min(r.first.second,r.second.second)
                val y2 = max(r.first.second, r.second.second)
                for (y in y1..y2) {
                    m.p(x,y)
                }
            } else if (r.first.second == r.second.second) {
                val y = r.first.second
                val x1 = min(r.first.first, r.second.first)
                val x2 = max(r.first.first, r.second.first)
                for (x in x1..x2) {
                    val p = Pair(x, y);
                    m[p] = (m[p]?:0) + 1
                }
            }
        }
        
        return m.filterValues { it > 1 }.size
    }

    fun part2(input: List<String>): Int {
        val m = mutableMapOf<Pair<Int,Int>,Int>().withDefault { 0 }
        input.map {
            val q = it.split(" ")
            coords(q[0]) to coords(q[2])
        }.forEach { r ->
            if (r.first.first == r.second.first) {
                val x = r.first.first
                val y1 = min(r.first.second,r.second.second)
                val y2 = max(r.first.second, r.second.second)
                for (y in y1..y2) {
                    m.p(x,y)
                }
            } else if (r.first.second == r.second.second) {
                val y = r.first.second
                val x1 = min(r.first.first, r.second.first)
                val x2 = max(r.first.first, r.second.first)
                for (x in x1..x2) {
                    m.p(x,y)
                }
            } else {
                var (x,y) = r.first
                val (x2,y2) = r.second
                val xinc = if (x < x2)  1 else -1
                val yinc = if (y < y2) 1 else -1
                m.p(x,y)
                do {
                    x += xinc
                    y += yinc
                    m.p(x,y)
                } while (x != x2)
            }
        }

        return m.filterValues { it > 1 }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)


    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
