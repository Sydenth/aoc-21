package year2021.day10

import utils.PuzzleInput
import utils.checkWithOutput
import java.util.*

fun main() {
    val puzzleInput = PuzzleInput(2021, 10) { input -> input.map(::Command) }

    checkWithOutput(day10_part1(puzzleInput.test), 26397)
    checkWithOutput(day10_part2(puzzleInput.test), 288957)

    println(day10_part1(puzzleInput.real))
    println(day10_part2(puzzleInput.real))
}

class Command(line: String) {
    private val parseResult = parseLine(line)

    fun getIllegalBracket(): Char? = (parseResult as? ParseResult.Corrupted)?.illegalBracket

    fun getMissingBrackets(): List<Char>? = (parseResult as? ParseResult.Incomplete)?.missingBrackets

    private fun parseLine(line: String): ParseResult {
        val bracketPairs = mapOf(
            '(' to ')',
            '[' to ']',
            '{' to '}',
            '<' to '>',
        )

        val stack = Stack<Char>()

        line.forEach { char ->
            when (char) {
                in bracketPairs.keys -> stack.push(char)
                in bracketPairs.values -> {
                    val latestBracket = stack.pop()
                    if (char != bracketPairs[latestBracket])
                        return ParseResult.Corrupted(char)
                }
                else -> error("Unknown character: $char")
            }
        }

        return ParseResult.Incomplete(stack.reversed().map(bracketPairs::getValue))
    }

    private sealed class ParseResult {
        data class Corrupted(val illegalBracket: Char): ParseResult()
        data class Incomplete(val missingBrackets: List<Char>): ParseResult()
    }
}

private fun day10_part1(input: List<Command>): Int {
    val scores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137,
    )

    return input
        .mapNotNull(Command::getIllegalBracket)
        .map(scores::getValue)
        .reduce(Int::plus)
}

private fun day10_part2(input: List<Command>): Long {
    val scores = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4,
    )

    return input
        .mapNotNull(Command::getMissingBrackets)
        .map { missingBrackets ->
            missingBrackets
                .map(scores::getValue)
                .fold(0L) { acc, score -> acc * 5 + score }
        }
        .sorted()
        .let { it[it.size / 2] }
}