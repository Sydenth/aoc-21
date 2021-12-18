package template

import utils.PuzzleInput
import utils.checkWithOutput
import utils.linesToInt

fun main() {
    val puzzleInput = PuzzleInput(0, 0, ::linesToInt)

    checkWithOutput(dayXY_part1(puzzleInput.tests[0]), 0)
//    checkWithOutput(dayXY_part2(puzzleInput.tests[0]), 0)

    println(dayXY_part1(puzzleInput.real))
//    println(dayXY_part2(puzzleInput.real))
}

private fun dayXY_part1(input: List<Int>): Int {
    return 0
}

private fun dayXY_part2(input: List<Int>): Int {
    return 0
}