package year2021.day07

import utils.PuzzleInput
import utils.checkWithOutput
import utils.printAll
import kotlin.math.abs
import kotlin.math.pow

fun main() {
    val puzzleInput = PuzzleInput(2021, 7) { input ->
        input.first().split(',').map(String::toInt)
    }

    checkWithOutput(day07_part1(puzzleInput.test), 37)
    checkWithOutput(day07_part2(puzzleInput.test), 168)

    println(day07_part1(puzzleInput.real))
    println(day07_part2(puzzleInput.real))
}

private fun day07_part1(input: List<Int>): Int {
    return solve(input) { steps ->
        steps
    }
}

private fun day07_part2(input: List<Int>): Int {
    return solve(input) { steps ->
        steps * (steps + 1) / 2 // == (0..steps).sum()
    }
}

private fun solve(input: List<Int>, fuelCost: (steps: Int) -> Int): Int {
    val range = input.minOrNull()!!..input.maxOrNull()!!

    return range.minOfOrNull { pos ->
        input.sumOf { targetPos ->
            val steps = abs(targetPos - pos)
            fuelCost(steps)
        }
    }!!
}