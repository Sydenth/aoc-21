package day01

import readInput
import java.io.File

fun main() {
    fun countIncreases(input: List<Int>): Int {
        return input
            .zipWithNext { prev, next -> next > prev }
            .count { it }
    }

    fun part1(input: List<Int>): Int {
        return countIncreases(input)
    }

    fun part2(input: List<Int>): Int {
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

    val testInput = readInput("day01", "Day01_test").map(String::toInt)
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("day01", "Day01").map(String::toInt)
    println(part1(input))
    println(part2(input))
}
