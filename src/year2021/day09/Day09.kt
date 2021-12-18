package year2021.day09

import utils.PuzzleInput
import utils.checkWithOutput
import utils.getNeighbors

fun main() {
    val puzzleInput = PuzzleInput(2021, 9) { input ->
        input.mapIndexed { y, row ->
            row.split("")
                .filter(String::isNotEmpty)
                .mapIndexed { x, str -> ValuePoint(x, y, str.toInt()) }
        }.let(::HeightMap)
    }

    checkWithOutput(day09_part1(puzzleInput.tests[0]), 15)
    checkWithOutput(day09_part2(puzzleInput.tests[0]), 1134)

    println(day09_part1(puzzleInput.real))
    println(day09_part2(puzzleInput.real))
}

data class ValuePoint(val x: Int, val y: Int, val value: Int) {
    operator fun compareTo(other: ValuePoint): Int = value.compareTo(other.value)
    override fun toString(): String = "(x$x y$y v$value)"
}

class HeightMap(val values: List<List<ValuePoint>>) {
    fun findMinima(): List<ValuePoint> {
        return values.flatMap { row ->
            row.filter { point ->
                val neighbors = values.getNeighbors(point.x, point.y, withDiagonal = false)
                neighbors.all { it.value > point.value }
            }
        }
    }

    fun findBasins(): List<Set<ValuePoint>> {
        return findMinima().map { minimum ->
            buildSet {
                add(minimum)

                var newPoints = listOf(minimum)
                while (newPoints.isNotEmpty()) {
                    newPoints = newPoints
                        .flatMap { values.getNeighbors(it.x, it.y, withDiagonal = false) }
                        .filter { it.value < 9 && it !in this }
                    addAll(newPoints)
                }
            }
        }
    }
}

private fun day09_part1(heightMap: HeightMap): Int {
    return heightMap.findMinima()
        .sumOf { it.value + 1 }
}

private fun day09_part2(heightMap: HeightMap): Int {
    return heightMap.findBasins()
        .map { it.size }
        .sorted()
        .takeLast(3)
        .reduce(Int::times)
}