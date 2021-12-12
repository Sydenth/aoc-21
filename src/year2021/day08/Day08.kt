package year2021.day08

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2021, 8) { input -> input.map(::WireInput) }

    puzzleInput.test(::day08_part1, listOf(26))
    puzzleInput.test(::day08_part2, listOf(61229))

    println(day08_part1(puzzleInput.real))
    println(day08_part2(puzzleInput.real))
}

class WireInput(input: String) {
    val patterns: List<Set<Char>>
    val output: List<Set<Char>>

    init {
        val (patternString, outputString) = input.split(" | ")
        patterns = patternString.split(" ").map(String::toSet)
        output = outputString.split(" ").map(String::toSet)
    }

    fun deduceDigits(): List<Int> {
        val digitToPattern: MutableMap<Int, Set<Char>> = mutableMapOf()
        val segmentToWire: MutableMap<Char, Char> = mutableMapOf()

        patterns.forEach { pattern ->
            when (pattern.size) {
                2 -> digitToPattern[1] = pattern
                3 -> digitToPattern[7] = pattern
                4 -> digitToPattern[4] = pattern
                7 -> digitToPattern[8] = pattern
            }
        }

        segmentToWire['a'] = digitToPattern[7]!!.minus(digitToPattern[1]!!).single()
        val cf = digitToPattern[1]!!
        val bd = digitToPattern[4]!!.minus(cf)
        digitToPattern[3] = patterns.find { it.size == 5 && it.minus(cf).size == 3 }!!
        segmentToWire['b'] = bd.minus(digitToPattern[3]!!).single()
        segmentToWire['d'] = bd.minus(segmentToWire['b']!!).single()
        digitToPattern[2] = patterns.find { it.size == 5 && it.minus(cf).size == 4 && it.minus(segmentToWire['b']).size == 5 }!!
        segmentToWire['f'] = cf.minus(digitToPattern[2]!!).single()
        segmentToWire['c'] = cf.minus(segmentToWire['f']!!).single()
        digitToPattern[5] = patterns.find { it.size == 5 && it.minus(bd).size == 3 && it.minus(cf).size == 4 }!!
        segmentToWire['g'] = digitToPattern[5]!!
            .minus(segmentToWire['a']!!)
            .minus(segmentToWire['b']!!)
            .minus(segmentToWire['d']!!)
            .minus(segmentToWire['f']!!)
            .single()
        segmentToWire['e'] = digitToPattern[8]!!
            .minus(segmentToWire['a']!!)
            .minus(segmentToWire['b']!!)
            .minus(segmentToWire['c']!!)
            .minus(segmentToWire['d']!!)
            .minus(segmentToWire['f']!!)
            .minus(segmentToWire['g']!!)
            .single()
        digitToPattern[6] = patterns.find { it.size == 6 && it.minus(segmentToWire['c']!!).size == 6 }!!
        digitToPattern[9] = patterns.find { it.size == 6 && it.minus(segmentToWire['e']!!).size == 6 }!!
        digitToPattern[0] = patterns.find { it.size == 6 && it.minus(segmentToWire['d']!!).size == 6 }!!

        val wireToSegment = segmentToWire.entries.associate { it.value to it.key }

        return output
            .map { wires ->
                val actualSegments: Set<Char> = wires.map { wire -> wireToSegment[wire]!! }.toSet()
                segmentsToDigit[actualSegments]!!
            }
    }

    companion object {
        val segmentsToDigit = mapOf(
            setOf('a', 'b', 'c', 'e', 'f', 'g') to 0,
            setOf('c', 'f') to 1,
            setOf('a', 'c', 'd', 'e', 'g') to 2,
            setOf('a', 'c', 'd', 'f', 'g') to 3,
            setOf('b', 'c', 'd', 'f') to 4,
            setOf('a', 'b', 'd', 'f', 'g') to 5,
            setOf('a', 'b', 'd', 'e', 'f', 'g') to 6,
            setOf('a', 'c', 'f') to 7,
            setOf('a', 'b', 'c', 'd', 'e', 'f', 'g') to 8,
            setOf('a', 'b', 'c', 'd', 'f', 'g') to 9,
        )
    }
}

private fun day08_part1(input: List<WireInput>): Int {
    return input.flatMap { it.deduceDigits() }.count { it == 1 || it == 4 || it == 7 || it == 8 }
}

private fun day08_part2(input: List<WireInput>): Int {
    return input.sumOf { it.deduceDigits().let { (a, b, c, d) -> a * 1000 + b * 100 + c * 10 + d } }
}