package year2021.day15

import utils.PuzzleInput
import utils.checkWithOutput
import utils.printAll
import utils.println

fun main() {
    val puzzleInput = PuzzleInput(2021, 15, ::DensityMap)

    checkWithOutput(day15_part1(puzzleInput.tests[0]), 40)
    checkWithOutput(day15_part2(puzzleInput.tests[0]), 315)

    println(day15_part1(puzzleInput.real))
    println(day15_part2(puzzleInput.real))
}

data class Node(
    val x: Int,
    val y: Int,
    val cost: Int,
    var prev: Node?,
    var distance: Long
)

class DensityMap(input: List<String>) {
    val tile: List<Node> = input.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            Node(x, y, c.digitToInt(), null, Long.MAX_VALUE)
        }
    }

    val width = tile.maxOf { it.x } + 1
    val height = tile.maxOf { it.y } + 1
}

class State(densityMap: DensityMap, tileCount: Int) {
    val nodes = List(tileCount) { dy ->
        List(tileCount) { dx ->
            densityMap.tile.map { node ->
                node.copy(
                    x = node.x + dx * densityMap.width,
                    y = node.y + dy * densityMap.height,
                    cost = ((node.cost + dy + dx - 1) % 9) + 1
                )
            }
        }.flatten()
    }.flatten()

    val startNode = nodes.first().also { it.distance = 0 }
    val endNode = nodes.last()

    val visited = mutableSetOf<Node>()
    val pending = mutableSetOf(startNode)

    fun advance() {
        val node = pending.minByOrNull { it.distance }!!
        val neighbors = getNeighbors(node).filter { it !in visited }

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

    fun getNeighbors(node: Node): List<Node> = nodes.filter { (x, y, _, _, _) ->
        y == node.y && (x == node.x - 1 || x == node.x + 1) || x == node.x && (y == node.y - 1 || y == node.y + 1)
    }
}

private fun day15_part1(densityMap: DensityMap): Long {
    val state = State(densityMap, tileCount = 1)
    while (state.pending.isNotEmpty()) state.advance()
    return state.endNode.distance
}

private fun day15_part2(densityMap: DensityMap): Long {
    val state = State(densityMap, tileCount = 5)
    while (state.pending.isNotEmpty()) state.advance()
    return state.endNode.distance
}