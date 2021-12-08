package utils

import java.nio.file.Paths
import kotlin.io.path.readLines

class PuzzleInput<T>(year: Int, day: Int, parse: (List<String>) -> T) {
    private val dayStr = "Day${day.toString().padStart(2, '0')}"
    private val path = "src/year$year/${dayStr.lowercase()}/$dayStr"

    val real: T = parse(Paths.get("$path.txt").readLines())
    val test: T = parse(Paths.get("${path}_test.txt").readLines())
}

fun PuzzleInput(year: Int, day: Int) = PuzzleInput(year, day) { it }