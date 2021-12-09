package year2021.day09

import utils.PuzzleInput
import utils.checkWithOutput
import utils.transpose

fun main() {
    val puzzleInput = PuzzleInput(2021, 9) { input ->
        input.mapIndexed { y, row ->
            row.split("")
                .filter(String::isNotEmpty)
                .mapIndexed { x, str -> ValuePoint(x, y, str.toInt()) }
        }.let(::HeightMap)
    }

    checkWithOutput(day09_part1(puzzleInput.test), 15)
    checkWithOutput(day09_part2(puzzleInput.test), 1134)

    println(day09_part1(puzzleInput.real))
    println(day09_part2(puzzleInput.real))
}

data class ValuePoint(val x: Int, val y: Int, val value: Int) {
    operator fun compareTo(other: ValuePoint): Int = value.compareTo(other.value)
    override fun toString(): String = "(x$x y$y v$value)"
}

class HeightMap(val values: List<List<ValuePoint>>) {
    fun findMinima(): Set<ValuePoint> {
        val horizontalLowPoints = findRowMinima(values)
        val verticalLowPoints = findRowMinima(values.transpose())
        return horizontalLowPoints.intersect(verticalLowPoints)
    }

    fun findBasins(): List<Set<ValuePoint>> {

        return findMinima().map { minimum ->
            buildSet {
                add(minimum)

                var newPoints = listOf(minimum)
                while (newPoints.isNotEmpty()) {
                    newPoints = newPoints
                        .flatMap { getDirectNeighbors(it) }
                        .filter { it.value < 9 && it !in this }
                    addAll(newPoints)
                }
            }
        }
    }

    private fun findRowMinima(rows: List<List<ValuePoint>>): Set<ValuePoint> {
        return buildSet {
            rows.forEach { row ->
                var lastPoint = ValuePoint(0, 0, Int.MAX_VALUE)
                var currentMinimum = ValuePoint(0, 0, Int.MAX_VALUE)

                row.forEachIndexed { index, point ->
                    if (point < lastPoint) {
                        currentMinimum = point
                        if (index == row.size - 1) add(point)
                    } else if (point > lastPoint) {
                        add(currentMinimum)
                    }
                    lastPoint = point
                }
            }
        }
    }

    private fun getDirectNeighbors(point: ValuePoint): List<ValuePoint> {
        return buildList {
            with(point) {
                if (x > 0) add(values[y][x - 1])
                if (x < values.first().size - 1) add(values[y][x + 1])
                if (y > 0) add(values[y - 1][x])
                if (y < values.size - 1) add(values[y + 1][x])
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
        .let { (a, b, c) -> a * b * c }
}