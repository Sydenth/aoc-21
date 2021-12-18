package year2021.day16

import utils.PuzzleInput
import utils.checkWithOutput

fun main() {
    val puzzleInput = PuzzleInput(2021, 16) { list ->
        list.first().map { char ->
            char.digitToInt(16)
                .toString(2)
                .padStart(4, '0')
        }.joinToString("")
    }

    checkWithOutput(day16_part1(puzzleInput.tests[0]), 14)
    checkWithOutput(day16_part1(puzzleInput.tests[1]), 16)
    checkWithOutput(day16_part1(puzzleInput.tests[2]), 12)
    checkWithOutput(day16_part1(puzzleInput.tests[3]), 23)
    checkWithOutput(day16_part1(puzzleInput.tests[4]), 31)

    println(day16_part1(puzzleInput.real))
    println(day16_part2(puzzleInput.real))
}

sealed class Packet {
    abstract val version: Int
    abstract val typeId: Int
    abstract val length: Int

    data class Literal(
        override val version: Int,
        override val typeId: Int,
        override val length: Int,
        val value: Long
    ) : Packet()

    data class Operator(
        override val version: Int,
        override val typeId: Int,
        override val length: Int,
        val subPackets: List<Packet>
    ) : Packet()
}

fun parseBits(bits: String, maxPackets: Int = Int.MAX_VALUE): List<Packet> {
    val minPacketLength = 7

    var index = 0
    var packetCount = 0

    return buildList {
        while (index < (bits.length - minPacketLength) && packetCount < maxPackets) {
            val version = bits.substring(index, index + 3).toInt(2); index += 3
            val typeId = bits.substring(index, index + 3).toInt(2); index += 3

            if (typeId == 4) {
                val startIndex = index

                val value = buildString {
                    var last: Boolean
                    do {
                        last = bits[index] == '0'; index++
                        append(bits.substring(index, index + 4)); index += 4
                    } while (!last)
                }.toLong(2)

                add(Packet.Literal(version, typeId, index - startIndex + 6, value))
                packetCount++
            }

            else {
                val lengthId = bits[index].digitToInt(2); index++

                if (lengthId == 0) {
                    val totalSubPacketLength = bits.substring(index, index + 15).toInt(2); index += 15
                    val subPackets = parseBits(bits.substring(index, index + totalSubPacketLength))
                    val actualSubPacketLength = subPackets.fold(0) { acc, packet -> acc + packet.length }
                    val packet = Packet.Operator(version, typeId, actualSubPacketLength + 22, subPackets)
                    add(packet)
                    index += packet.length - 22
                    packetCount++
                }

                else {
                    val subPacketNumber = bits.substring(index, index + 11).toInt(2); index += 11
                    val subPackets = parseBits(bits.substring(index), subPacketNumber)
                    val actualSubPacketLength = subPackets.fold(0) { acc, packet -> acc + packet.length }
                    val packet = Packet.Operator(version, typeId, actualSubPacketLength + 18, subPackets)
                    add(packet)
                    index += packet.length - 18
                    packetCount++
                }
            }
        }
    }
}

private fun day16_part1(bits: String): Int {
    fun reduceVersions(packet: Packet): Int = when (packet) {
        is Packet.Literal -> packet.version
        is Packet.Operator -> packet.version + packet.subPackets.map(::reduceVersions).reduce(Int::plus)
    }

    val packet = parseBits(bits).single()
    return reduceVersions(packet)
}

private fun day16_part2(bits: String): Long {
    fun calculateExpression(packet: Packet): Long= when (packet) {
        is Packet.Literal -> packet.value
        is Packet.Operator -> when (packet.typeId) {
            0 -> packet.subPackets.map(::calculateExpression).reduce(Long::plus)
            1 -> packet.subPackets.map(::calculateExpression).reduce(Long::times)
            2 -> packet.subPackets.minOf(::calculateExpression)
            3 -> packet.subPackets.maxOf(::calculateExpression)
            5 -> packet.subPackets.map(::calculateExpression).let { (a, b) -> if (a > b) 1 else 0 }
            6 -> packet.subPackets.map(::calculateExpression).let { (a, b) -> if (a < b) 1 else 0 }
            7 -> packet.subPackets.map(::calculateExpression).let { (a, b) -> if (a == b) 1 else 0 }
            else -> 0L
        }
    }

    val packet = parseBits(bits).single()
    return calculateExpression(packet)
}