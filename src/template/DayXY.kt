package template

import utils.PuzzleInput
import utils.checkWithOutput
import utils.linesToInt

fun main() {
    val puzzleInput = PuzzleInput(0, 0, ::linesToInt)

    puzzleInput.test(::dayXY_part1, listOf(1))
//    puzzleInput.test(::dayXY_part2, listOf(1))

    println(dayXY_part1(puzzleInput.real))
//    println(dayXY_part2(puzzleInput.real))
}

private fun dayXY_part1(input: List<Int>): Int {
    return 0
}

private fun dayXY_part2(input: List<Int>): Int {
    return 0
}