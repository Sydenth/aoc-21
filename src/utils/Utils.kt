package utils

import java.math.BigInteger
import java.nio.file.Paths
import java.security.MessageDigest
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.io.path.readLines
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

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

fun linesToInt(input: List<String>): List<Int> = input.map(String::toInt)
fun linesToLong(input: List<String>): List<Long> = input.map(String::toLong)