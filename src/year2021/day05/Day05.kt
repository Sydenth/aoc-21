package year2021.day05

import utils.PuzzleInput
import utils.checkWithOutput
import java.lang.Integer.max

fun main() {
    val puzzleInput = PuzzleInput(2021, 5, ::parseInput)

    puzzleInput.test(::day05_part1, listOf(5))
    puzzleInput.test(::day05_part2, listOf(12))

    println(day05_part1(puzzleInput.real))
    println(day05_part2(puzzleInput.real))
}

private data class Point(val x: Int, val y: Int)

private data class Line(val start: Point, val end: Point) {
    private val xRange = if (start.x <= end.x) start.x..end.x else start.x downTo end.x
    private val yRange = if (start.y <= end.y) start.y..end.y else start.y downTo end.y

    val isDiagonal = start.x != end.x && start.y != end.y
    val points = when {
        xRange.count() == 1 -> yRange.map { Point(xRange.first, it) }
        yRange.count() == 1 -> xRange.map { Point(it, yRange.first) }
        else -> xRange.zip(yRange).map { (x, y) -> Point(x, y) }
    }
}

private fun parseInput(input: List<String>): List<Line> {
    return input.map { line ->
        line
            .split(" -> ", ",")
            .map(String::toInt)
            .let { (x1, y1, x2, y2) -> Line(Point(x1, y1), Point(x2, y2)) }
    }
}

private fun day05_part1(input: List<Line>): Int {
    return countIntersections(input.filterNot(Line::isDiagonal))
}

private fun day05_part2(input: List<Line>): Int {
    return countIntersections(input)
}

private fun countIntersections(input: List<Line>): Int {
    return input
        .flatMap { line -> line.points }
        .groupingBy { it }
        .eachCount()
        .count { (_, count) -> count > 1 }
}