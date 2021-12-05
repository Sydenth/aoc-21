package year2021.day01

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2021, 1, String::toInt)

    checkWithOutput(day01_part1(puzzleInput.test), 7)
    checkWithOutput(day01_part2(puzzleInput.test), 5)

    println(day01_part1(puzzleInput.real))
    println(day01_part2(puzzleInput.real))
}

fun day01_part1(input: List<Int>): Int {
    return countIncreases(input)
}

fun day01_part2(input: List<Int>): Int {
    val transformed = input
        .windowed(
            size = 3,
            step = 1,
            partialWindows = false
        ) { window ->
            window.sum()
        }
    return countIncreases(transformed)
}

fun countIncreases(input: List<Int>): Int {
    return input
        .zipWithNext { prev, next -> next > prev }
        .count { it }
}