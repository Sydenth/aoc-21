package utils

import kotlin.math.abs

fun <T> checkWithOutput(actual: T, expected: T) {
    if (actual == expected) {
        println("$actual == $expected: Check passed")
    } else {
        error("$actual == $expected: Check failed")
    }
}

fun printAll(vararg obj: Any) {
    println(obj.joinToString(", "))
}

fun Collection<*>.println(): Unit = forEach(::println)

fun linesToInt(input: List<String>): List<Int> = input.map(String::toInt)
fun linesToLong(input: List<String>): List<Long> = input.map(String::toLong)

fun <T> List<T>.rows(columns: Int): List<List<T>> {
    return windowed(columns, columns)
}

fun <T> List<T>.columns(columns: Int): List<List<T>> {
    return (0 until columns).map { y ->
        IntProgression.fromClosedRange(y, size - 1, columns).let(::slice)
    }
}

fun <T> List<List<T>>.transpose(): List<List<T>> {
    if (isEmpty()) return this

    check(isQuadratic()) { "All nested lists must have the same size, but sizes were ${map { it.size }}" }

    return List(first().size) { col ->
        List(size) { row ->
            this[row][col]
        }
    }
}

fun <T> List<List<T>>.getNeighbors(x: Int, y: Int, withDiagonal: Boolean): List<T> {
    check(isQuadratic()) { "All nested lists must have the same size, but sizes were ${map { it.size }}" }

    val list = this

    return buildList {
        for (dx in -1..1) {
            for (dy in -1..1) {
                if (
                    (dx != 0 || dy != 0)
                    && (x + dx) in list.first().indices
                    && (y + dy) in list.indices
                    && (withDiagonal || abs(dx) + abs(dy) != 2)
                ) add(list[y + dy][x + dx])
            }
        }
    }
}

fun List<List<*>>.isQuadratic(): Boolean {
    val width = first().size
    return all { it.size == width }
}