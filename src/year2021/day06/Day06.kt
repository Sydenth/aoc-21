package year2021.day06

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2021, 6) { input -> input.first().split(',').map(String::toInt) }

    puzzleInput.test(::day06_part1, listOf(5934L))
    puzzleInput.test(::day06_part2, listOf(26984457539L))

    println(day06_part1(puzzleInput.real))
    println(day06_part2(puzzleInput.real))
}

private fun parseLine(line: String): List<Int> = line.split(',').map(String::toInt)

private fun day06_part1(input: List<Int>): Long {
    return simulateFishGrowth(input, 80)
}

private fun day06_part2(input: List<Int>): Long {
    return simulateFishGrowth(input, 256)
}

private fun simulateFishGrowth(input: List<Int>, days: Int): Long {
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