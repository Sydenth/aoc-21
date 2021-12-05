package year2021.day05

import utils.PuzzleInput
import utils.checkWithOutput
import java.lang.Integer.max

fun main() {
    val puzzleInput = PuzzleInput(2021, 5, ::parseInput)

    checkWithOutput(dayXY_part1(puzzleInput.test), 5)
    checkWithOutput(dayXY_part2(puzzleInput.test), 12)

    println(dayXY_part1(puzzleInput.real))
    println(dayXY_part2(puzzleInput.real))
}

private data class Point(val x: Int, val y: Int)

private data class Line(val start: Point, val end: Point) {
    val isDiagonal = start.x != end.x && start.y != end.y
    val xRange = if (start.x <= end.x) start.x..end.x else start.x downTo end.x
    val xDiff = end.x - start.x
    val yDiff = end.y - start.y
    val yRange = if (start.y <= end.y) start.y..end.y else start.y downTo end.y
}

private fun parseInput(line: String): Line {
    return line
        .split(" -> ")
        .map { range ->
            range
                .split(',')
                .map(String::toInt)
                .let { (x, y) ->
                    Point(x, y)
                }
        }
        .let { (start, end) ->
            Line(start, end)
        }
}

private fun dayXY_part1(input: List<Line>): Int {
    return countIntersections(input.filterNot(Line::isDiagonal))
}

private fun dayXY_part2(input: List<Line>): Int {
    return countIntersections(input)
}

private fun countIntersections(input: List<Line>): Int {
    val pointMap = mutableMapOf<Point, Int>()

    input.forEach { line ->
        val maxLength = max(line.xRange.count(), line.yRange.count())

        repeat(maxLength) { step ->
            val x = step * (line.xDiff / (maxLength - 1)) + line.start.x
            val y = step * (line.yDiff / (maxLength - 1)) + line.start.y
            pointMap.merge(Point(x, y), 1, Int::plus)
        }
    }

    return pointMap.values.count { it >= 2 }
}