import java.io.File
import java.math.BigInteger
import java.nio.file.Paths
import java.security.MessageDigest
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.io.path.readLines
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

/**
 * Reads lines from the given input txt file.
 */
fun readInput(dir: String, name: String) = Paths.get("src", dir, "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

@OptIn(ExperimentalTime::class)
fun <T> measure(tag: String, block: () -> T): T {
    val (value, duration) = measureTimedValue(block)
    println("$tag: $duration")
    return value
}

@OptIn(ExperimentalContracts::class)
fun checkWithOutput(value: Boolean) {
    contract {
        returns() implies value
    }
    check(value) { "Check failed." }
    println("Check passed")
}