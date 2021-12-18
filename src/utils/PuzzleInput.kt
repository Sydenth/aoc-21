package utils

import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.readLines

class PuzzleInput<T>(year: Int, day: Int, parse: (List<String>) -> T) {
    private val dayStr = "Day${day.toString().padStart(2, '0')}"
    private val path = "src/year$year/${dayStr.lowercase()}/$dayStr"

    val real: T = parse(Paths.get("$path.txt").readLines())

    val tests: List<T> = (1..9).mapNotNull { index ->
        val indexStr = if (index == 1) "" else index.toString()
        Paths.get("${path}_test$indexStr.txt").readLinesOrNull()
    }.map(parse)
}

private fun Path.readLinesOrNull(): List<String>? {
    return try {
        readLines()
    } catch (e: IOException) {
        null
    }
}