package year2021.day06

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2021, 6) { it }

    val testInput = parseLine(puzzleInput.test.first())
    checkWithOutput(day06_part1(testInput), 5934L)
    checkWithOutput(day06_part2(testInput), 26984457539L)

    val input = parseLine(puzzleInput.real.first())
    println(day06_part1(input))
    println(day06_part2(input))
}

fun parseLine(line: String): List<Int> = line.split(',').map(String::toInt)

fun day06_part1(input: List<Int>): Long {
    return simulateFishGrowth(input, 80)
}

fun day06_part2(input: List<Int>): Long {
    return simulateFishGrowth(input, 256)
}

fun simulateFishGrowth(input: List<Int>, days: Int): Long {
    val ageMap = input
        .groupBy { it }
        .mapValues { (_, count) -> count.size.toLong() }
        .toMutableMap()

    repeat(days) {
        val reproducing = ageMap[0] ?: 0
        for (i in 0..8) {
            ageMap[i] = ageMap[i + 1] ?: 0
        }
        ageMap.merge(6, reproducing, Long::plus)
        ageMap[8] = reproducing
    }

    return ageMap.values.sum()
}