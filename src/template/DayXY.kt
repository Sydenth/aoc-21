package template

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(0, 0, String::toInt)

    checkWithOutput(dayXY_part1(puzzleInput.test), 1)
    checkWithOutput(dayXY_part2(puzzleInput.test), 1)

    println(dayXY_part1(puzzleInput.real))
    println(dayXY_part2(puzzleInput.real))
}

fun dayXY_part1(input: List<Int>): Int {
    return input.size
}

fun dayXY_part2(input: List<Int>): Int {
    return input.size
}