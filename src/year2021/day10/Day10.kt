package year2021.day10

import utils.PuzzleInput
import utils.checkWithOutput
import java.util.*

fun main() {
    val puzzleInput = PuzzleInput(2021, 10) { input -> input.map(::Command) }

    puzzleInput.test(::day10_part1, listOf(26397))
    puzzleInput.test(::day10_part2, listOf(288957))

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

        line.forEach { bracket ->
            when (bracket) {
                in bracketPairs.keys -> stack.push(bracket)
                in bracketPairs.values -> {
                    val latestBracket = stack.pop()
                    if (bracket != bracketPairs[latestBracket])
                        return ParseResult.Corrupted(bracket)
                }
                else -> error("Unknown character: $bracket")
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