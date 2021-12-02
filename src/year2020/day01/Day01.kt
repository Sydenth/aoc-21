package year2020.day01

import PuzzleInput
import checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2020, 1, String::toInt)

    checkWithOutput(day01_part1(puzzleInput.test), 1)
    checkWithOutput(day01_part2(puzzleInput.test), 1)

    println(day01_part1(puzzleInput.real))
    println(day01_part2(puzzleInput.real))
}

fun day01_part1(input: List<Int>): Int {
    return input.size
}

fun day01_part2(input: List<Int>): Int {
    return input.size
}