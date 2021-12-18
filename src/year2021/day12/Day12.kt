package year2021.day12

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2021, 12, ::CaveConnections)

    checkWithOutput(dayXY_part1(puzzleInput.tests[0]), 10)
    checkWithOutput(dayXY_part1(puzzleInput.tests[1]), 19)
    checkWithOutput(dayXY_part1(puzzleInput.tests[2]), 226)
    checkWithOutput(dayXY_part2(puzzleInput.tests[0]), 0)

    println(dayXY_part1(puzzleInput.real))
//    println(dayXY_part2(puzzleInput.real))
}

class CaveConnections(input: List<String>) {
    private val caves: MutableMap<String, List<String>> = mutableMapOf()

    init {
        input.forEach { line ->
            val (start, end) = line.split("-")
            caves.merge(start, listOf(end), List<String>::plus)
            caves.merge(end, listOf(start), List<String>::plus)
        }
    }
}

private fun dayXY_part1(caveConnections: CaveConnections): Int {
    return 0
}

private fun dayXY_part2(caveConnections: CaveConnections): Int {
    return 0
}