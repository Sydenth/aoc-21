package day01

import readInput

fun main() {
    val testInput = readInput("day01", "Day01_test").map(String::toInt)
    check(day01_part1(testInput) == 7)
    check(day01_part2(testInput) == 5)

    val input = readInput("day01", "Day01").map(String::toInt)
    println(day01_part1(input))
    println(day01_part2(input))
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