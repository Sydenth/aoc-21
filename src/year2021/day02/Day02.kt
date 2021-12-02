package year2021.day02

import PuzzleInput
import checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2021, 2, ::parseLine)

    checkWithOutput(day02_part1(puzzleInput.test), 150)
    checkWithOutput(day02_part2(puzzleInput.test), 900)

    println(day02_part1(puzzleInput.real))
    println(day02_part2(puzzleInput.real))
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

fun parseLine(line: String): Command {
    val (direction, amount) = line.split(' ')
    return Command(Direction.of(direction), amount.toInt())
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