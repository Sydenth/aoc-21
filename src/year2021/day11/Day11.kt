package year2021.day11

import utils.PuzzleInput
import utils.checkWithOutput
import utils.getNeighbors

fun main() {
    val puzzleInput = PuzzleInput(2021, 11) { lines ->
        lines.mapIndexed { y, line ->
            line.split("")
                .filter(String::isNotEmpty)
                .mapIndexed { x, str ->
                    ValuePoint(x, y, str.toInt())
                }
        }.let(::Grid)
    }

    checkWithOutput(day11_part1(puzzleInput.tests[0]), 1656)
    checkWithOutput(day11_part2(puzzleInput.tests[0]), 195)

    println(day11_part1(puzzleInput.real))
    println(day11_part2(puzzleInput.real))
}

data class ValuePoint(val x: Int, val y: Int, val value: Int)

data class Grid(val values: List<List<ValuePoint>>) {
    fun advance(): Grid {
        val mutableGrid = values.map { row ->
            row.map { point ->
                point.copy(value = point.value + 1)
            }.toMutableList()
        }

        val flashed = mutableListOf<ValuePoint>()
        var newlyFlashed: List<ValuePoint>

        do {
            newlyFlashed = mutableGrid
                .flatten()
                .filter { it.value > 9 && it !in flashed }
                .also(flashed::addAll)

            newlyFlashed
                .flatMap { mutableGrid.getNeighbors(it.x, it.y, withDiagonal = true) }
                .filter { it !in flashed }
                .forEach { (x, y, _) ->
                    val point = mutableGrid[y][x]
                    mutableGrid[y][x] = point.copy(value = point.value + 1)
                }
        } while (newlyFlashed.isNotEmpty())

        mutableGrid.forEach { row ->
            row.replaceAll {
                it.copy(value = if (it.value > 9) 0 else it.value)
            }
        }
        return Grid(mutableGrid)
    }
}

private fun day11_part1(input: Grid): Int {
    var grid = input
    val count = (0 until 100).fold(0) { acc, _ ->
        grid = grid.advance()
        val flashes = grid.values.flatten().count { it.value == 0 }
        acc + flashes
    }
    return count
}

private fun day11_part2(input: Grid): Int {
    var grid = input
    var step = 0

    while (true) {
        step++
        grid = grid.advance()
        if (grid.values.flatten().all { it.value == 0 }) break
    }
    return step
}