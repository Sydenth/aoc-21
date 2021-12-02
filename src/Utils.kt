import java.math.BigInteger
import java.nio.file.Paths
import java.security.MessageDigest
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.io.path.readLines
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

inline fun <reified T> checkWithOutput(actual: T, expected: T) {
    if (actual == expected) {
        println("$actual == $expected: Check passed")
    } else {
        error("$actual == $expected: Check failed")
    }
}

class PuzzleInput<T>(year: Int, day: Int, transform: (String) -> T) {
    private val path = "src/year$year/day${day.toString().padStart(2, '0')}"
    val real: List<T> = Paths.get("$path.txt").readLines().map(transform)
    val test: List<T> = Paths.get("${path}_test.txt").readLines().map(transform)
}