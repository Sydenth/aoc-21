package year2020.day10

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2020, 10, String::toInt)

    checkWithOutput(day10_part1(puzzleInput.test), 220)
//    checkWithOutput(day10_part2(puzzleInput.test), 19208)

    println(day10_part1(puzzleInput.real))
//    println(day10_part2(puzzleInput.real))
}

fun day10_part1(input: List<Int>): Int {
    val extendedInput = getExtendedInput(input)
    val resultMap = extendedInput.zipWithNext { a, b -> b - a }.groupingBy { it }.eachCount()
    return resultMap[1]!! * resultMap[3]!!
}

fun day10_part2(input: List<Int>): Int {
    return input.size
}

fun getExtendedInput(input: List<Int>): List<Int> {
    val outlet = 0
    val phone = (input.maxOrNull() ?: 0) + 3
    return input.plus(outlet).plus(phone).sorted()
}