package year2020.day02

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2020, 2, ::parseLine)

    checkWithOutput(day02_part1(puzzleInput.test), 2)
    checkWithOutput(day02_part2(puzzleInput.test), 1)

    println(day02_part1(puzzleInput.real))
    println(day02_part2(puzzleInput.real))
}

data class PasswordPolicy(
    val range: IntRange,
    val char: Char
)

fun parseLine(line: String): Pair<PasswordPolicy, String> {
    val split = line.split(" ")

    return PasswordPolicy(
        range = with(split[0]) {
            val (start, end) = split("-").map(String::toInt)
            start..end
        },
        char = split[1][0]
    ) to split[2]
}

fun day02_part1(input: List<Pair<PasswordPolicy, String>>): Int {
    return input.count { (policy, password) ->
        password.count { it == policy.char } in policy.range
    }
}

fun day02_part2(input: List<Pair<PasswordPolicy, String>>): Int {
    return input.count { (policy, password) ->
        (password[policy.range.first - 1] == policy.char) xor (password[policy.range.last - 1] == policy.char)
    }
}