package year2020.day10

import utils.PuzzleInput
import utils.checkWithOutput
import utils.linesToInt

fun main() {
    val puzzleInput = PuzzleInput(2020, 10, ::linesToInt)

    checkWithOutput(day10_part1(puzzleInput.tests[0]), 220)
//    checkWithOutput(day10_part2(puzzleInput.tests[0]), 19208)

    println(day10_part1(puzzleInput.real))
//    println(day10_part2(puzzleInput.real))
}

private fun day10_part1(input: List<Int>): Int {
    val extendedInput = getExtendedInput(input)
    val resultMap = extendedInput.zipWithNext { a, b -> b - a }.groupingBy { it }.eachCount()
    return resultMap[1]!! * resultMap[3]!!
}

private fun day10_part2(input: List<Int>): Int {
    return input.size
}

private fun getExtendedInput(input: List<Int>): List<Int> {
    val outlet = 0
    val phone = (input.maxOrNull() ?: 0) + 3
    return input.plus(outlet).plus(phone).sorted()
}