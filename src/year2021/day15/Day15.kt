package year2021.day15

import utils.PuzzleInput

fun main() {
    val puzzleInput = PuzzleInput(2021, 15, ::State)

    puzzleInput.test(::day15_part1, listOf(315))
//    puzzleInput.test(::day15_part2, listOf(1))

    println(day15_part1(puzzleInput.real))
//    println(day15_part2(puzzleInput.real))
}

data class Node(
    val x: Int,
    val y: Int,
    val cost: Int,
    var prev: Node?,
    var distance: Int
)

class State(input: List<String>) {
    val nodes = input.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            Node(x, y, c.digitToInt(), null, Int.MAX_VALUE)
        }
    }

    val startNode = nodes.find { (x, y, _, _, _) -> x == 0 && y == 0 }!!.also { it.distance = 0 }
    val endNode = nodes.find { (x, y, _, _, _) -> x == input.last().length - 1 && y == input.size - 1 }!!

    val visited = mutableSetOf<Node>()
    val pending = mutableSetOf(startNode)

    fun advance() {
        val node = pending.minByOrNull { it.distance }!!
        val neighbors = nodes.getNeighbors(node).filter { it !in visited }

        neighbors.forEach {
            val distance = node.distance + it.cost

            if (distance < it.distance) {
                it.prev = node
                it.distance = distance
            }
        }

        visited.add(node)
        pending.remove(node)
        pending.addAll(neighbors)
    }
}

fun List<Node>.getNeighbors(node: Node): List<Node> = filter { (x, y, _, _, _) ->
    y == node.y && (x == node.x - 1 || x == node.x + 1) || x == node.x && (y == node.y - 1 || y == node.y + 1)
}

private fun day15_part1(state: State): Int {
    while (state.pending.isNotEmpty()) {
        state.advance()
    }

    return state.endNode.distance
}

private fun day15_part2(state: State): Int {
    return 0
}