package year2021.day03

import utils.PuzzleInput
import utils.checkWithOutput
import kotlin.math.pow

fun main() {
    val puzzleInput = PuzzleInput(2021, 3) { it }

    checkWithOutput(day03_part1(puzzleInput.test), 198)
    checkWithOutput(day03_part2(puzzleInput.test), 230)

    println(day03_part1(puzzleInput.real))
    println(day03_part2(puzzleInput.real))
}

fun day03_part1(input: List<String>): Int {
    val binaryLength = input.first().length

    val gamma = (0 until binaryLength)
        .map { pos ->
            val count = input.count { line -> line[pos] == '1' }
            if (count > input.size/2f) 1 else 0
        }
        .joinToString("")
        .toInt(2)
    val epsilon = gamma xor (2f.pow(binaryLength).toInt() - 1)

    return gamma * epsilon
}

fun day03_part2(input: List<String>): Int {
    val oxygen = filter(input, keepMostCommon = true)
    val co2 = filter(input, keepMostCommon = false)

    return oxygen * co2
}


fun filter(input: List<String>, keepMostCommon: Boolean): Int {
    val binaryLength = input.first().length

    val greaterChar = if (keepMostCommon) '1' else '0'
    val lesserChar = if (keepMostCommon) '0' else '1'

    return (0 until binaryLength)
        .fold(input) { acc, pos ->
            if (acc.size == 1) {
                acc
            } else {
                val count = acc.count { line -> line[pos] == '1' }
                val filterChar = if (count >= acc.size / 2f) greaterChar else lesserChar
                acc.filter { line -> line[pos] == filterChar }
            }
        }
        .single()
        .toInt(2)
}