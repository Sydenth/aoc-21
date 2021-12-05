package utils

import java.math.BigInteger
import java.nio.file.Paths
import java.security.MessageDigest
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.io.path.readLines
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

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

//fun <T> List<T>.mutate(block: MutableList<T>.() -> Unit): List<T> = toMutableList().apply(block)