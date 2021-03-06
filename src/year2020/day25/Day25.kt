package year2020.day25

import utils.PuzzleInput
import utils.checkWithOutput
import utils.linesToLong

fun main() {
    val puzzleInput = PuzzleInput(2020, 25, ::linesToLong)

    checkWithOutput(day25_part1(puzzleInput.tests[0]), 14897079)
    println(day25_part1(puzzleInput.real))
}

private fun day25_part1(input: List<Long>): Long {
    val cardPubKey = input[0]
    val doorPubKey = input[1]

    val loopSizeCard = getLoopSize(cardPubKey)

    var encryptionKey = 1L
    for (i in 1..loopSizeCard) {
        encryptionKey = transform(encryptionKey, doorPubKey)
    }

    return encryptionKey
}

private fun getLoopSize(pubKey: Long): Int {
    var loopSize = 0
    var transformed = 1L
    while (transformed != pubKey) {
        transformed = transform(transformed, 7)
        loopSize++
    }
    return loopSize
}

private fun transform(value: Long, subjectNumber: Long): Long = (value * subjectNumber) % 20201227