package year2021.day07

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2021, 7) { input ->
        input.first().split(',').map(String::toInt)
    }

    checkWithOutput(day07_part1(puzzleInput.test), 1)
    checkWithOutput(day07_part2(puzzleInput.test), 1)

    println(day07_part1(puzzleInput.real))
    println(day07_part2(puzzleInput.real))
}

fun day07_part1(input: List<Int>): Int {
    return input.size
}

fun day07_part2(input: List<Int>): Int {
    return input.size
}