package template

import readInput

fun main() {
    val testInput = readInput("dayXY", "DayXY_test")
    check(dayXY_part1(testInput) == 1)
    check(dayXY_part2(testInput) == 1)

    val input = readInput("dayXY", "DayXY")
    println(dayXY_part1(input))
    println(dayXY_part2(input))
}

fun dayXY_part1(input: List<String>): Int {
    return input.size
}

fun dayXY_part2(input: List<String>): Int {
    return input.size
}