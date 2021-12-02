package day02

import checkWithOutput
import readInput

fun main() {
    val testInput = readInput("day02", "Day02_test")
    val preparedTestInput = prepareInput(testInput)
    checkWithOutput(day02_part1(preparedTestInput) == 150)
    checkWithOutput(day02_part2(preparedTestInput) == 900)

    val input = readInput("day02", "Day02")
    val preparedInput = prepareInput(input)
    println(day02_part1(preparedInput))
    println(day02_part2(preparedInput))
}

data class Command(
    val direction: Direction,
    val amount: Int
)

enum class Direction {
    Forward,
    Down,
    Up;

    companion object {
        private val valuesByName = values().associateBy { it.name.lowercase() }
        fun of(input: String): Direction = valuesByName[input.lowercase()] ?: error("unsupported direction: $input")
    }
}

data class Position(
    val horizontal: Int = 0,
    val depth: Int = 0,
    val aim: Int = 0
)

fun prepareInput(input: List<String>): List<Command> {
    val regex = """(\w+) (\d+)""".toRegex()

    return input.map { line ->
        val (direction, amount) = regex.find(line)?.destructured ?: error("malformed input: $line")
        Command(Direction.of(direction), amount.toInt())
    }
}

fun day02_part1(input: List<Command>): Int {
    val finalPosition = input.fold(Position()) { acc, (direction, amount) ->
        when (direction) {
            Direction.Forward -> acc.copy(horizontal = acc.horizontal + amount)
            Direction.Down -> acc.copy(depth = acc.depth + amount)
            Direction.Up -> acc.copy(depth = acc.depth - amount)
        }
    }

    return finalPosition.horizontal * finalPosition.depth
}

fun day02_part2(input: List<Command>): Int {
    val finalPosition = input.fold(Position()) { acc, (direction, amount) ->
        when (direction) {
            Direction.Forward -> acc.copy(horizontal = acc.horizontal + amount, depth = acc.depth + amount * acc.aim)
            Direction.Down -> acc.copy(aim = acc.aim + amount)
            Direction.Up -> acc.copy(aim = acc.aim - amount)
        }
    }

    return finalPosition.horizontal * finalPosition.depth
}