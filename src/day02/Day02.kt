package day02

import readInput

fun main() {
    val testInput = readInput("day02", "Day02_test")
    check(day02_part1(testInput) == 1)
    check(day02_part2(testInput) == 1)

    val input = readInput("day02", "Day02")
    println(day02_part1(input))
    println(day02_part2(input))
}

fun day02_part1(input: List<String>): Int {
    return input.size
}

fun day02_part2(input: List<String>): Int {
    return input.size
}