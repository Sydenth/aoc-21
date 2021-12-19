package year2021.day14

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2021, 14) { list ->
        list.first() to list.drop(2).associate { line ->
            val (pair, insertion) = line.split(" -> ")
            pair to insertion.single()
        }
    }

    checkWithOutput(day14_part1(puzzleInput.tests[0]), 1588L)
    checkWithOutput(day14_part2(puzzleInput.tests[0]), 2188189693529L)

    println(day14_part1(puzzleInput.real))
    println(day14_part2(puzzleInput.real))
}

fun applyInsertions(template: String, insertions: Map<String, Char>, times: Int): Map<Char, Long> {
    var pairs = template
        .zipWithNext { a, b -> "$a$b" }
        .associateWith { 1L }
        .toMutableMap()

    repeat(times) {
        val newPairs = mutableMapOf<String, Long>()

        pairs.filterValues { it > 0 }.forEach { (pair, count) ->
            insertions[pair]?.let {
                val pairA = "${pair.first()}$it"
                val pairB = "$it${pair.last()}"

                newPairs.merge(pairA, count, Long::plus)
                newPairs.merge(pairB, count, Long::plus)
            }
        }

        pairs = newPairs
    }

    val charCounts = mutableMapOf<Char, Long>()
    pairs.forEach { (pair, count) ->
        charCounts[pair.first()] = (charCounts[pair.first()] ?: 0L) + count
    }
    charCounts[template.last()] = (charCounts[template.last()] ?: 0L) + 1

    return charCounts
}


private fun day14_part1(input: Pair<String, Map<String, Char>>): Long {
    val (template, insertions) = input
    val result = applyInsertions(template, insertions, 10)
    return result.maxOf { it.value } - result.minOf { it.value }
}

private fun day14_part2(input: Pair<String, Map<String, Char>>): Long {
    val (template, insertions) = input
    val result = applyInsertions(template, insertions, 40)
    return result.maxOf { it.value } - result.minOf { it.value }
}