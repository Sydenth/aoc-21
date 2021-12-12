package year2020.day01

import utils.PuzzleInput
import utils.checkWithOutput
import utils.linesToInt
import java.util.*

fun main() {
    val puzzleInput = PuzzleInput(2020, 1, ::linesToInt)

    puzzleInput.test(::day01_part1, listOf(514579))
    puzzleInput.test(::day01_part2, listOf(241861950))

    println(day01_part1(puzzleInput.real))
    println(day01_part2(puzzleInput.real))
}

private fun day01_part1(input: List<Int>): Int {
    input.indices.forEach { firstIndex ->
        val first = input[firstIndex]
        input.drop(firstIndex + 1).forEach { second ->
            if (first + second == 2020) return first * second
        }
    }
    return -1
}

private fun day01_part2(input: List<Int>): Int {
    input.indices.forEach { firstIndex ->
        val first = input[firstIndex]
        input.drop(firstIndex + 1).indices.forEach { secondIndex ->
            val second = input[firstIndex + secondIndex]
            input.drop(firstIndex + secondIndex + 2).forEach { third ->
                if (first + second + third == 2020) return first * second * third
            }
        }
    }
    return -1
}