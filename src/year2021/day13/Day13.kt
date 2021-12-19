package year2021.day13

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2021, 13, ::parseInput)

    checkWithOutput(day13_part1(puzzleInput.tests[0]), 17)

    println(day13_part1(puzzleInput.real))
    day13_part2(puzzleInput.real)
}

private data class Instructions(val points: List<Point>, val folds: List<Fold>)
private data class Point(val x: Int, val y: Int)
private data class Fold(val axis: Axis, val lineNumber: Int)
private enum class Axis { X, Y }

private fun parseInput(input: List<String>): Instructions {
    val points = input
        .takeWhile(String::isNotEmpty)
        .map { line ->
            line
                .split(",")
                .map(String::toInt)
                .let { (x, y) -> Point(x, y) }
        }
    val folds = input
        .takeLastWhile(String::isNotEmpty)
        .map { line ->
            line
                .removePrefix("fold along ")
                .split("=")
                .let { (axis, lineNumber) -> Fold(Axis.valueOf(axis.uppercase()), lineNumber.toInt()) }
        }

    return Instructions(points, folds)
}

private fun foldPaper(points: List<Point>, fold: Fold): List<Point> {
    val (firstSide, secondSide) = when (fold.axis) {
        Axis.X -> points.partition { it.x < fold.lineNumber }
        Axis.Y -> points.partition { it.y < fold.lineNumber }
    }

    val secondSideTransformed = secondSide.map {
        when (fold.axis) {
            Axis.X -> it.copy(x = 2 * fold.lineNumber - it.x)
            Axis.Y -> it.copy(y = 2 * fold.lineNumber - it.y)
        }
    }

    return firstSide.plus(secondSideTransformed).distinct()
}

private fun day13_part1(input: Instructions): Int {
    return foldPaper(input.points, input.folds.first()).size
}

private fun day13_part2(input: Instructions): Int {
    val result = input.folds.fold(input.points) { points, fold ->
        foldPaper(points, fold)
    }

    val width = result.maxOf { it.x }
    val height = result.maxOf { it.y }

    for (y in 0..height) {
        for (x in 0..width) {
            if (Point(x, y) in result) print("#") else print(" ")
        }
        println()
    }

    return 0
}