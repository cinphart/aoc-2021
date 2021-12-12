fun main() {

    fun paths( node: String, edges: Map<String, List<String>>, visited: Set<String>) : Int {
        if (node == "end") return 1
        val seen = if (node.all { it.isLowerCase()}) visited.plus(node) else visited
        return edges[node]!!.filter { it !in seen }.fold(0) {
            a, nn ->
                a + paths(nn, edges, seen)
        }
    }

    fun part1(input: List<String>): Int {
        val matcher = Regex("([a-zA-Z]+)-([a-zA-Z]+)")
        val edges = input.flatMap {
            matcher.find(it)?.groupValues?.let {
                listOf(Pair(it[1],it[2]), Pair(it[2],it[1]))
            }!!
        }.groupBy { it.first }.mapValues { e ->
            e.value.map { p -> p.second }
        }

        val result = paths("start", edges, setOf("start"));
        return result
    }

    fun paths2(node: String, edges: Map<String, List<String>>, visited: Map<String, Int>) : Int {
        if (node == "end") return 1
        if (visited[node] == 2 || visited[node] == 1 && visited.values.any { it == 2}) return 0
        val seen = if (node.all { it.isLowerCase()}) visited.plus(node to (visited[node]?:0)+1) else visited
        return edges[node]!!.filter{ it != "start"}.fold(0) {
                a, nn ->
            println("Checking paths for $node -> $nn (${visited})")
            a + paths2(nn, edges, seen)
        }
    }

    fun part2(input: List<String>): Int {
        val matcher = Regex("([a-zA-Z]+)-([a-zA-Z]+)")
        val edges = input.flatMap {
            matcher.find(it)?.groupValues?.let {
                listOf(Pair(it[1],it[2]), Pair(it[2],it[1]))
            }!!
        }.groupBy { it.first }.mapValues { e ->
            e.value.map { p -> p.second }
        }

        val result = paths2("start", edges, mapOf("start" to 0 ));
        println("result: ${result}")
        return result
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 10)

    val input = readInput("Day12")
    println(part1(input))

    check(part2(testInput) == 36)
    println(part2(input))
}
